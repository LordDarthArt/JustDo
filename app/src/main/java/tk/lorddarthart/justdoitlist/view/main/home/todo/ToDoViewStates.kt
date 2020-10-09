package tk.lorddarthart.justdoitlist.view.main.home.todo

import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import java.lang.Exception

sealed class ToDoViewStates {
    class ToDoError(val exception: Exception): ToDoViewStates()
    class ToDoLoaded(val toDoList: MutableList<ToDoItemDayModel>): ToDoViewStates()

    object ToDoLoading: ToDoViewStates()
    object ToDoEmpty: ToDoViewStates()
}