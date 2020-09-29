package tk.lorddarthart.justdoitlist.model.pojo.main

data class ToDoItemModel(
        val comment: String? = null,
        val completed: Boolean? = null,
        val notify: Long? = null,
        val priority: Long? = null,
        val timestamp: Long? = null,
        val title: String? = null
)