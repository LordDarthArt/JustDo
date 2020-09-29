package tk.lorddarthart.justdoitlist.presentation.main.todo.add

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.presentation.main.todo.add.AddFragmentView
import tk.lorddarthart.justdoitlist.util.helper.logDebug

@InjectViewState
class AddPresenter: BaseFragmentPresenter<AddFragmentView>() {
    var priority = 0L

    fun onTick(priorityTag: String) {
        viewState.replacePriority(priorityTag)
        logDebug { "Priority was set to $priorityTag" }
    }
}