package tk.lorddarthart.justdo.application.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tk.lorddarthart.justdo.R
import tk.lorddarthart.justdo.application.BaseActivity
import tk.lorddarthart.justdo.application.main.todo.model.ToDoItemDayModel
import tk.lorddarthart.justdo.application.main.todo.model.ToDoItemModel
import tk.lorddarthart.justdo.application.main.todo.view.NoToDosFragment
import tk.lorddarthart.justdo.application.main.todo.view.ToDoFragment
import tk.lorddarthart.justdo.utils.comparators.CompareObjects
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

class LoadingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mActivity: BaseActivity
    private lateinit var mView: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

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
        mView = inflater.inflate(R.layout.fragment_loading, container, false)
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
                            }
                            mActivity.mToDoDay = tododay
                            val fragment = ToDoFragment()
                            mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_todo, fragment).commit()
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

    private fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    companion object {

        private const val TAG = "LoadingFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LoadingFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
