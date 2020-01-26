package tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.app.App

import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragmentView
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.baseNavigator
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

@InjectViewState
class ToDoPresenter: BaseFragmentPresenter<ToDoFragmentView>() {
    fun openAddToDo() {
        baseNavigator.navigate(AddFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim)
    }
}