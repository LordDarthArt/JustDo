package tk.lorddarthart.justdoitlist.util.comparators

import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel

class CompareObjectsToDoItemDayModel {

    companion object : Comparator<ToDoItemDayModel> {

        override fun compare(a: ToDoItemDayModel, b: ToDoItemDayModel): Int = when {
            a.listToDoModel!![0].timestamp != b.listToDoModel!![0].timestamp -> (a.listToDoModel[0].timestamp!! - b.listToDoModel[0].timestamp!!).toInt()
            else -> (b.listToDoModel[0].timestamp!! - a.listToDoModel[0].timestamp!!).toInt()
        }
    }

}