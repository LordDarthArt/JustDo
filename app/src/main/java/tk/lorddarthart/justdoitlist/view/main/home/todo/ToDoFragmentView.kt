package tk.lorddarthart.justdoitlist.view.main.home.todo

import com.arellomobile.mvp.MvpView
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainView
import java.lang.Exception

interface ToDoFragmentView: BaseMainView {
    fun noTasks()
    fun onFailure(e: Exception)
    fun showToDo()
}