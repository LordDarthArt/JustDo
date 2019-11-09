package tk.lorddarthart.justdoitlist.util.comparators

import tk.lorddarthart.justdoitlist.app.model.model.ToDoItemDayModel

class CompareObjectsToDoItemDayModel {

    companion object : Comparator<ToDoItemDayModel> {

        override fun compare(a: ToDoItemDayModel, b: ToDoItemDayModel): Int = when {
            a.mListToDoModel[0].timestamp != b.mListToDoModel[0].timestamp -> (a.mListToDoModel[0].timestamp!! - b.mListToDoModel[0].timestamp!!).toInt()
            else -> (b.mListToDoModel[0].timestamp!! - a.mListToDoModel[0].timestamp!!).toInt()
        }
    }

}