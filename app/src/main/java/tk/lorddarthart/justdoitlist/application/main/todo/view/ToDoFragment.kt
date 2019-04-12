package tk.lorddarthart.justdoitlist.application.main.todo.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_todo.view.*
import tk.lorddarthart.justdoitlist.utils.CompareObjects
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.main.todo.controller.ToDoViewAdapter
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemModel
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.application.main.todo.add.AddActivity
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ToDoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        Inflate the layout for this fragment
//        container?.removeAllViews() - Maybe i don't need this???
        mView = inflater.inflate(R.layout.fragment_todo, container, false)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val sdf2 = SimpleDateFormat("EEE, dd.MM.yyyy")
        val todo: MutableList<ToDoItemModel> = mutableListOf()
        val tododay: MutableList<ToDoItemDayModel> = mutableListOf()
        if (FirebaseAuth.getInstance().currentUser!=null) {
            FirebaseFirestore.getInstance().collection("todo").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("list").get().addOnSuccessListener { tasks ->
                if (!tasks.isEmpty) {
                    for (docSnap in tasks.documents) {
                        FirebaseFirestore.getInstance().collection("todo").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("list").document(docSnap.id).collection("todoofday").get().addOnSuccessListener { tasks2 ->
                            if (!tasks2.isEmpty) {
                                tododay.clear()
                                todo.clear()
                                val title: String? = sdf2.format(sdf.parse(docSnap.getString("day")))
                                for (g in tasks.documents) {
                                    for (i in tasks2.documents) {
                                        todo.add(ToDoItemModel(i.getLong("priority"), i.getString("title"), i.getString("comment"), i.getLong("timestamp"), i.getLong("notify"), i.getBoolean("completed")))
                                    }
                                    todo.sortWith(CompareObjects)
                                    tododay.add(ToDoItemDayModel(title, todo))
                                }
                                initializeAdapter(mView, tododay)
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "There was a problem")
                }
            }
        }
        mView.btnAdd.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity!!, AddActivity::class.java))
        }
        return mView
    }

    private fun initializeAdapter(view: View, todoofday: List<ToDoItemDayModel>) {
        val recyclerViewAdapter = ToDoViewAdapter(activity, todoofday)
        view.rvToDo.adapter = recyclerViewAdapter
        recyclerViewAdapter.setReturnCount(todoofday.size)
        val layoutManager = LinearLayoutManager(activity)
        view.rvToDo.layoutManager = layoutManager
    }

    companion object {

        private const val TAG = "ToDoFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ToDoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
