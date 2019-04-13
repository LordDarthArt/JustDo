package tk.lorddarthart.justdo.utils.comparators

import tk.lorddarthart.justdo.application.main.todo.model.ToDoItemModel

class CompareObjects {

    companion object : Comparator<ToDoItemModel> {

        override fun compare(a: ToDoItemModel, b: ToDoItemModel): Int = when {
            a.timestamp != b.timestamp -> (a.timestamp!! - b.timestamp!!).toInt()
            else -> (b.timestamp!! - a.timestamp!!).toInt()
        }
    }
}