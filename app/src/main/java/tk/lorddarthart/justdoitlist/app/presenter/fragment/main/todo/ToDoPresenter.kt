package tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo

import com.arellomobile.mvp.InjectViewState

import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragmentView
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.BaseNavigator
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

@InjectViewState
class ToDoPresenter: BaseFragmentPresenter<ToDoFragmentView>() {
    fun openAddToDo() {
        BaseNavigator.navigate(AddFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim)
    }
}