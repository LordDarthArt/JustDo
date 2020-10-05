package tk.lorddarthart.justdoitlist.bussiness.main.todo.add

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import tk.lorddarthart.justdoitlist.view.main.home.todo.add.AddFragmentView

@InjectViewState
class AddPresenter: BaseFragmentPresenter<AddFragmentView>() {
    var priority = 0L

    fun onTick(priorityTag: String) {
        viewState.replacePriority(priorityTag)
        logDebug { "Priority was set to $priorityTag" }
    }

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}