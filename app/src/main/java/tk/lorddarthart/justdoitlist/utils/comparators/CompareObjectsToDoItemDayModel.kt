package tk.lorddarthart.justdoitlist.utils.comparators

import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemDayModel

class CompareObjectsToDoItemDayModel {

    companion object : Comparator<ToDoItemDayModel> {

        override fun compare(a: ToDoItemDayModel, b: ToDoItemDayModel): Int = when {
            a.mListToDoModel[0].timestamp != b.mListToDoModel[0].timestamp -> (a.mListToDoModel[0].timestamp!! - b.mListToDoModel[0].timestamp!!).toInt()
            else -> (b.mListToDoModel[0].timestamp!! - a.mListToDoModel[0].timestamp!!).toInt()
        }
    }

}