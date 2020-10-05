package tk.lorddarthart.justdoitlist.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.model.holder.ToDoHolder
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemModel
import tk.lorddarthart.justdoitlist.util.comparators.CompareObjectsToDoItemDayModel
import tk.lorddarthart.justdoitlist.util.comparators.CompareObjectsToDoItemModel
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.constants.cloudfirestorestructure.CloudFirestoreMainPage
import tk.lorddarthart.justdoitlist.util.constants.cloudfirestorestructure.todo.year.month.day.CloudFirestoreToDoYearMonthDay
import tk.lorddarthart.justdoitlist.util.constants.cloudfirestorestructure.todo.year.month.day.todoofday.CloudFirestoreToDoDayItem
import tk.lorddarthart.justdoitlist.util.converters.DayTitleConverter
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.justdoitlist.util.helper.RequestDataCallback
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import tk.lorddarthart.justdoitlist.util.helper.logError
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemoteRepositoryImpl: RemoteRepository, Loggable {
    @Inject lateinit var toDoHolder: ToDoHolder
    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var localeHelper: LocaleHelper

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }

    override fun accessTheRemoteData(requestDataCallback: RequestDataCallback) {
        val fromDatabaseToTimestamp = SimpleDateFormat(DateFormatsTemplates.fromDatabaseToTimestamp)
        val tododay = mutableListOf<ToDoItemDayModel>()
        if (auth.currentUser != null) {
            FirebaseFirestore.getInstance().collection(CloudFirestoreMainPage.field_todo_name)
                .document(FirebaseAuth.getInstance().currentUser!!.uid).collection(
                    SimpleDateFormat(DateFormatsTemplates.year).format(System.currentTimeMillis()).toString())
                .get().addOnSuccessListener { years ->
                    if (!years.isEmpty) {
                        for (year in years.documents) {
                            year.reference.collection(
                                SimpleDateFormat(DateFormatsTemplates.month).format(System.currentTimeMillis()).toString()
                            ).get().addOnSuccessListener { month ->
                                if (!month.isEmpty) {
                                    tododay.clear()
                                    for ((z, day) in month.documents.withIndex()) {
                                        val mFromTimestampToTitle = SimpleDateFormat(
                                            DateFormatsTemplates.getFromTimestampToTitle(toCalendar(fromDatabaseToTimestamp.parse(day.reference.id + "." + month.documents[0].reference.parent.id + "." + year.reference.parent.id)), localeHelper)
                                        )
                                        val title: String = DayTitleConverter.convertToPreferred(
                                            mFromTimestampToTitle,
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
                                                requestDataCallback.onSuccess()
                                            }
                                        }.addOnFailureListener {
                                            logError(it) { "Problem: " }
                                        }
                                    }
                                } else {
                                    requestDataCallback.onEmpty()
                                }
                            }.addOnFailureListener {
                                requestDataCallback.onFailure(it)
                            }
                        }
                    } else {
                        requestDataCallback.onEmpty()
                    }
                }.addOnFailureListener {
                    requestDataCallback.onFailure(it)
                }
        }
    }

    private fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }
}