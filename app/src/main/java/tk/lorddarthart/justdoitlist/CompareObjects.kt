package tk.lorddarthart.justdoitlist

class CompareObjects {

    companion object : Comparator<ToDoItem> {

        override fun compare(a: ToDoItem, b: ToDoItem): Int = when {
            a.timestamp != b.timestamp -> (a.timestamp!! - b.timestamp!!).toInt()
            else -> (b.timestamp!! - a.timestamp!!).toInt()
        }
    }
}