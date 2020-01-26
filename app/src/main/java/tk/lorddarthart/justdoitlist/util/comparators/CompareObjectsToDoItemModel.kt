package tk.lorddarthart.justdoitlist.util.comparators

import tk.lorddarthart.justdoitlist.app.model.pojo.main.ToDoItemModel

class CompareObjectsToDoItemModel {

    companion object : Comparator<ToDoItemModel> {

        override fun compare(a: ToDoItemModel, b: ToDoItemModel): Int = when {
            a.timestamp != b.timestamp -> (a.timestamp!! - b.timestamp!!).toInt()
            else -> (b.timestamp!! - a.timestamp!!).toInt()
        }
    }
}