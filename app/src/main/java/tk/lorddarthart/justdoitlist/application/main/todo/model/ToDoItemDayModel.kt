package tk.lorddarthart.justdoitlist.application.main.todo.model

class ToDoItemDayModel {
    var titleDay: String? = ""
    var mListToDoModel: List<ToDoItemModel>

    constructor(titleDay: String?, listToDoModel: List<ToDoItemModel>) {
        this.titleDay = titleDay
        this.mListToDoModel = listToDoModel
    }
}