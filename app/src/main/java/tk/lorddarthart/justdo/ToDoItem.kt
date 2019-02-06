package tk.lorddarthart.justdo

class ToDoItem {
    var priority: Int? = 0
    var title: String?
    var comment: String?
    var timestamp: Long? = 0

    constructor(priority: Int?, title: String?, comment: String?, timestamp: Long?) {
        this.priority = priority
        this.title = title
        this.comment = comment
        this.timestamp = timestamp
    }
}