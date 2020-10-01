package tk.lorddarthart.justdoitlist.model.holder

import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel

class ToDoHolderImpl: ToDoHolder {
    override var toDoList = mutableListOf<ToDoItemDayModel>()
}