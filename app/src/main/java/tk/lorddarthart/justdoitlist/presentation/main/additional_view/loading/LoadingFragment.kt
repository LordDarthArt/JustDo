package tk.lorddarthart.justdoitlist.presentation.main.additional_view.loading

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemModel
import tk.lorddarthart.justdoitlist.presentation.main.base.BaseMainTabFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentLoadingBinding
import tk.lorddarthart.justdoitlist.util.comparators.CompareObjectsToDoItemDayModel
import tk.lorddarthart.justdoitlist.util.comparators.CompareObjectsToDoItemModel
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.constants.cloudfirestorestructure.CloudFirestoreMainPage
import tk.lorddarthart.justdoitlist.util.constants.cloudfirestorestructure.todo.year.month.day.list.CloudFirestoreToDoYearMonthDay
import tk.lorddarthart.justdoitlist.util.constants.cloudfirestorestructure.todo.year.month.day.list.todoofday.CloudFirestoreToDoDayItem
import tk.lorddarthart.justdoitlist.util.converters.DayTitleConverter
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import tk.lorddarthart.justdoitlist.util.helper.logError
import java.text.SimpleDateFormat
import java.util.*

class LoadingFragment : BaseMainTabFragment(), LoadingFragmentView {
    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentLoadingBinding.inflate(inflater, container, false)
    }

    private fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun noTasks() {
        logDebug { "Currently no tasks" }

        if (this@LoadingFragment.isVisible) {
            router.moveToNoToDos()
        }
    }

    private fun onFailure(e: Exception) {
        logError(e) { "data request to Firebase failed: ${e.message}" }

        if (this@LoadingFragment.isVisible) {
            router.moveToError()
        }
    }

    override fun initListeners() {
        // do nothing
    }

    override fun start() {
        val fromDatabaseToTimestamp = SimpleDateFormat(DateFormatsTemplates.fromDatabaseToTimestamp)
        val tododay = mutableListOf<ToDoItemDayModel>()
        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            FirebaseFirestore.getInstance().collection(CloudFirestoreMainPage.field_todo_name)
                    .document(currentUser.uid).collection(
                            SimpleDateFormat(DateFormatsTemplates.mYear).format(System.currentTimeMillis()).toString())
                    .get().addOnSuccessListener { years ->
                        if (!years.isEmpty) {
                            for (year in years.documents) {
                                year.reference.collection(
                                        SimpleDateFormat(DateFormatsTemplates.mMonth).format(System.currentTimeMillis()).toString()
                                ).get().addOnSuccessListener { month ->
                                    if (!month.isEmpty) {
                                        tododay.clear()
                                        for ((z, day) in month.documents.withIndex()) {
                                            val fromTimestampToTitle = SimpleDateFormat(
                                                    DateFormatsTemplates.getFromTimestampToTitle(
                                                            toCalendar(fromDatabaseToTimestamp.parse(
                                                                    day.reference.id + "." + month.documents[0].reference.parent.id + "." + year.reference.parent.id
                                                            )), activity)
                                            )
                                            val title: String = DayTitleConverter.convertToPreferred(
                                                    fromTimestampToTitle,
                                                    fromDatabaseToTimestamp.parse(day.reference.id + "." + month.documents[0].reference.parent.id + "." + year.reference.parent.id)
                                            )
                                            day.reference.collection(
                                                    CloudFirestoreToDoYearMonthDay.field_todoofday_name
                                            ).get().addOnSuccessListener { tasks ->
                                                val todo: MutableList<ToDoItemModel> = arrayListOf()
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
                                                    todo.sortWith(CompareObjectsToDoItemModel)
                                                }
                                                tododay.add(ToDoItemDayModel(title, todo))
                                                tododay.sortWith(CompareObjectsToDoItemDayModel)
                                                if (z == month.documents.lastIndex) {
                                                    with(toDoHolder.toDoList) {
                                                        clear()
                                                        addAll(tododay)
                                                    }
                                                    router.moveToToDoList()
                                                }
                                            }.addOnFailureListener {
                                                logError(it) { "Problem: ${it.message}" }
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
    }

    companion object {
        var INSTANCE: LoadingFragment? = null
    }
}
