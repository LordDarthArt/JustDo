package tk.lorddarthart.justdoitlist.application.main.view

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
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemModel
import tk.lorddarthart.justdoitlist.application.main.todo.view.ToDoFragment
import tk.lorddarthart.justdoitlist.utils.comparators.CompareObjects
import tk.lorddarthart.justdoitlist.utils.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.utils.constants.cloudfirestorestructure.CloudFirestoreMainPage
import tk.lorddarthart.justdoitlist.utils.constants.cloudfirestorestructure.todo.list.CloudFirestoreToDoDay
import tk.lorddarthart.justdoitlist.utils.constants.cloudfirestorestructure.todo.list.todoofday.CloudFirestoreToDoDayItem
import tk.lorddarthart.justdoitlist.utils.converters.DayTitleConverter
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoadingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mActivity: BaseActivity
    private lateinit var mView: View
    private val mDays = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")

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
            FirebaseFirestore.getInstance().collection(CloudFirestoreMainPage.field_todo_name)
                    .document(FirebaseAuth.getInstance().currentUser!!.uid).collection(
                            SimpleDateFormat(DateFormatsTemplates.mYear).format(System.currentTimeMillis()).toString())
                    .get().addOnSuccessListener { years ->
                        if (!years.isEmpty) {
                            for (year in years.documents) {
                                year.reference.collection(
                                        SimpleDateFormat(DateFormatsTemplates.mMonth).format(System.currentTimeMillis()).toString()
                                ).get().addOnSuccessListener { months ->
                                    if (!months.isEmpty) {
                                        for (month in months.documents) {
                                            month.reference.collection(
                                                    SimpleDateFormat(DateFormatsTemplates.mDay).format(System.currentTimeMillis()).toString()
                                            ).get().addOnSuccessListener { days ->
                                                if (!days.isEmpty) {
                                                    tododay.clear()
                                                    todo.clear()
                                                    for (day in days.documents) {
                                                        day.reference.collection(
                                                                CloudFirestoreToDoDay.field_todoofday_name
                                                        ).get().addOnSuccessListener { tasks ->
                                                                    val mFromTimestampToTitle = SimpleDateFormat(
                                                                            DateFormatsTemplates.getFromTimestampToTitle(
                                                                                    toCalendar(mFromDatabaseToTimestamp.parse(
                                                                                               day.reference.parent.id + "." + month.reference.parent.id + "." + year.reference.parent.id
                                                                                            )
                                                                                    ), mActivity)
                                                                    )
                                                                    val title: String = DayTitleConverter(
                                                                            mFromTimestampToTitle,
                                                                            mFromDatabaseToTimestamp.parse(day.reference.parent.id + "." + month.reference.parent.id + "." + year.reference.parent.id)
                                                                    ).convertToPreferred()
                                                                    for (newDay in days.documents) {
                                                                        for (newTask in tasks.documents) {
                                                                            todo.add(
                                                                                    ToDoItemModel(newTask.getString(CloudFirestoreToDoDayItem.field_comment_name),
                                                                                            newTask.getBoolean(CloudFirestoreToDoDayItem.field_completed_name),
                                                                                            newTask.getLong(CloudFirestoreToDoDayItem.field_notify_name),
                                                                                            newTask.getLong(CloudFirestoreToDoDayItem.field_priority_name),
                                                                                            newTask.getLong(CloudFirestoreToDoDayItem.field_timestamp_name),
                                                                                            newTask.getString(CloudFirestoreToDoDayItem.field_title_name)
                                                                                    )
                                                                            )
                                                                            todo.sortWith(CompareObjects)
                                                                            tododay.add(ToDoItemDayModel(title, todo))
                                                                        }
                                                                    }
                                                                    mActivity.mToDoDay = tododay
                                                                    val fragment = ToDoFragment()
                                                                    mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_todo, fragment).commit()
                                                                }.addOnFailureListener {
                                                                    Log.e(TAG, "Problem", it)
                                                                }
                                                    }
                                                } else {
                                                    noTasks()
                                                }
                                            }.addOnFailureListener {
                                                onFailure(it)
                                            }
                                        }
                                    } else {
                                        noTasks()
                                    }
                                }.addOnFailureListener {
                                    onFailure(it)
                                }
                            }
                        } else {
                            noTasks()
                        }
                    }.addOnFailureListener {
                        onFailure(it)
                    }
        }
        return mView
    }

    private fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun noTasks() {
        Log.d(TAG, "Currently no tasks")

        val fragment = NoToDosFragment()
        mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_todo, fragment).commit()
    }

    private fun onFailure(e: Exception) {
        Log.d(TAG, "data request to Firebase failed. You know why? Oh, this is simple: ", e)

        val fragment = ErrorFragment()
        mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_todo, fragment).commit()
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
