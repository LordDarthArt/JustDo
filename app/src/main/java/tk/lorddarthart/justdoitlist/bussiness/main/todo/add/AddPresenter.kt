package tk.lorddarthart.justdoitlist.bussiness.main.todo.add

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import tk.lorddarthart.justdoitlist.view.main.add.AddFragmentView
import java.text.SimpleDateFormat
import java.util.*

@InjectViewState
class AddPresenter: BaseFragmentPresenter<AddFragmentView>() {
    var priority = 0L

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }

    fun onTick(priorityTag: String) {
        viewState.replacePriority(priorityTag)
        logDebug { "Priority was set to $priorityTag" }
    }

    fun getCurrentTime(): String? {
        val currentDate = Date(System.currentTimeMillis())
        return SimpleDateFormat("HH:mm").format(currentDate)
    }
}