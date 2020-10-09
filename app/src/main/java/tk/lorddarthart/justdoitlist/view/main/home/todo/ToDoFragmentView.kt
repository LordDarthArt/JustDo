package tk.lorddarthart.justdoitlist.view.main.home.todo

import tk.lorddarthart.justdoitlist.view.main.base.BaseMainView

interface ToDoFragmentView : BaseMainView {
    fun render(toDoViewState: ToDoViewStates)
}