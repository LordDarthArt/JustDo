package tk.lorddarthart.justdo.application.main.todo.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_todo.view.*
import tk.lorddarthart.justdo.utils.comparators.CompareObjects
import tk.lorddarthart.justdo.R
import tk.lorddarthart.justdo.application.BaseActivity
import tk.lorddarthart.justdo.application.main.todo.controller.ToDoViewAdapter
import tk.lorddarthart.justdo.application.main.todo.model.ToDoItemModel
import tk.lorddarthart.justdo.application.main.todo.model.ToDoItemDayModel
import tk.lorddarthart.justdo.application.main.todo.add.AddFragment
import tk.lorddarthart.justdo.utils.constants.DateFormatsTemplates
import tk.lorddarthart.justdo.utils.constants.cloudfirestorestructure.CloudFirestoreMainPage
import tk.lorddarthart.justdo.utils.constants.cloudfirestorestructure.todo.CloudFirestoreToDo
import tk.lorddarthart.justdo.utils.constants.cloudfirestorestructure.todo.list.CloudFirestoreToDoDay
import tk.lorddarthart.justdo.utils.constants.cloudfirestorestructure.todo.list.todoofday.CloudFirestoreToDoDayItem
import tk.lorddarthart.justdo.utils.converters.DayTitleConverter
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ToDoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity
    private lateinit var mButtonAdd: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        Inflate the layout for this fragment
//        container?.removeAllViews() - Maybe i don't need this???
        mView = inflater.inflate(R.layout.fragment_todo, container, false)
        mButtonAdd = mView.findViewById(R.id.btnAdd)
        val mFromDatabaseToTimestamp = SimpleDateFormat(DateFormatsTemplates.mFromDatabaseToTimestamp)
        val todo: MutableList<ToDoItemModel> = mutableListOf()
        val tododay: MutableList<ToDoItemDayModel> = mutableListOf()
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseFirestore.getInstance().collection(
                    CloudFirestoreMainPage.field_todo_name
            ).document(FirebaseAuth.getInstance().currentUser!!.uid).collection(
                    CloudFirestoreToDo.field_list_name
            ).get().addOnSuccessListener { tasks ->
                if (!tasks.isEmpty) {
                    for (docSnap in tasks.documents) {
                        FirebaseFirestore.getInstance().collection(
                                CloudFirestoreMainPage.field_todo_name
                        ).document(FirebaseAuth.getInstance().currentUser!!.uid).collection(
                                CloudFirestoreToDo.field_list_name
                        ).document(docSnap.id).collection(
                                CloudFirestoreToDoDay.field_todoofday_name
                        ).get().addOnSuccessListener { tasks2 ->
                            if (!tasks2.isEmpty) {
                                tododay.clear()
                                todo.clear()
                                val mFromTimestampToTitle = SimpleDateFormat(
                                        DateFormatsTemplates.getFromTimestampToTitle(
                                                toCalendar(mFromDatabaseToTimestamp.parse(
                                                        docSnap.getString(CloudFirestoreToDoDay.field_day_name
                                                        )
                                                )), mActivity)
                                )
                                val title: String = DayTitleConverter(mFromTimestampToTitle, mFromDatabaseToTimestamp.parse(docSnap.getString(CloudFirestoreToDoDay.field_day_name))).convertToPreferred()
                                for (g in tasks.documents) {
                                    for (i in tasks2.documents) {
                                        todo.add(
                                                ToDoItemModel(i.getString(CloudFirestoreToDoDayItem.field_comment_name),
                                                        i.getBoolean(CloudFirestoreToDoDayItem.field_completed_name),
                                                        i.getLong(CloudFirestoreToDoDayItem.field_notify_name),
                                                        i.getLong(CloudFirestoreToDoDayItem.field_priority_name),
                                                        i.getLong(CloudFirestoreToDoDayItem.field_timestamp_name),
                                                        i.getString(CloudFirestoreToDoDayItem.field_title_name)
                                                )
                                        )
                                    }
                                    todo.sortWith(CompareObjects)
                                    tododay.add(ToDoItemDayModel(title, todo))
                                }
                                initializeAdapter(mView, tododay)
                                mButtonAdd.setOnClickListener {
                                    val fragment = AddFragment()
                                    mActivity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragment).addToBackStack(null).commit()
                                }
                                mButtonAdd.setBackgroundDrawable(mActivity.resources.getDrawable(R.drawable.shape_circle))
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "Currently no tasks")

                    val fragment = NoToDosFragment()
                    mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit()
                }
            }
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

    private fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
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
