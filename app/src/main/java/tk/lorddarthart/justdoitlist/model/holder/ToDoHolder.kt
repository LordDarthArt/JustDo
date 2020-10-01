package tk.lorddarthart.justdoitlist.model.holder

import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel

interface ToDoHolder {
    var toDoList: MutableList<ToDoItemDayModel>
}