package tk.lorddarthart.justdoitlist.application.main.todo.model

class ToDoItemModel {
    var priority: Long? = 0
    var title: String?
    var comment: String?
    var timestamp: Long? = 0
    var notify: Long? = 0
    var completed: Boolean? = false

    constructor(priority: Long?, title: String?, comment: String?, timestamp: Long?, notify: Long?, completed: Boolean?) {
        this.priority = priority
        this.title = title
        this.comment = comment
        this.timestamp = timestamp
        this.notify = notify
        this.completed = completed
    }
}