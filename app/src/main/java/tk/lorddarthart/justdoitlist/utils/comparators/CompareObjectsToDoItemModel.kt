package tk.lorddarthart.justdoitlist.utils.comparators

import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemModel

class CompareObjectsToDoItemModel {

    companion object : Comparator<ToDoItemModel> {

        override fun compare(a: ToDoItemModel, b: ToDoItemModel): Int = when {
            a.timestamp != b.timestamp -> (a.timestamp!! - b.timestamp!!).toInt()
            else -> (b.timestamp!! - a.timestamp!!).toInt()
        }
    }
}