package tk.lorddarthart.justdoitlist


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.text.SimpleDateFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        container?.removeAllViews()
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val sdf2 = SimpleDateFormat("EEE, dd.MM.yyyy")
        val todo: MutableList<ToDoItem> = mutableListOf()
        val tododay: MutableList<ToDoItemDay> = mutableListOf()
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
                                        todo.add(ToDoItem(i.getLong("priority"), i.getString("title"), i.getString("comment"), i.getLong("timestamp"), i.getLong("notify"), i.getBoolean("completed")))
                                    }
                                    todo.sortWith(CompareObjects)
                                    tododay.add(ToDoItemDay(title, todo))
                                }
                                initializeAdapter(view!!, tododay)
                            }
                        }
                    }
                } else {

                }
            }
        }
        view.btnAdd.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity!!, AddActivity::class.java))
        }
        return view
    }

    private fun initializeAdapter(view: View, todoofday: List<ToDoItemDay>) {
        val recyclerViewAdapter = RecyclerViewAdapter(activity, todoofday)
        view.rvToDo.adapter = recyclerViewAdapter
        recyclerViewAdapter.setReturnCount(todoofday.size)
        val layoutManager = LinearLayoutManager(activity)
        view.rvToDo.layoutManager = layoutManager
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
