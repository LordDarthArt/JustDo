package tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragmentView
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject

@InjectViewState
class ToDoPresenter: BaseFragmentPresenter<ToDoFragmentView>() {
    @Inject lateinit var navUtils: NavUtils

    fun openAddToDo() {
        navUtils.baseNavigator?.navigate(AddFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim)
    }
}