package tk.lorddarthart.justdoitlist

class ToDoItemDay {
    var titleDay: String? = ""
    var listToDo: List<ToDoItem>

    constructor(titleDay: String?, listToDo: List<ToDoItem>) {
        this.titleDay = titleDay
        this.listToDo = listToDo
    }
}