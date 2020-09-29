package tk.lorddarthart.justdoitlist.presentation.main.todo.add

import tk.lorddarthart.justdoitlist.presentation.main.base.BaseMainView
import tk.lorddarthart.justdoitlist.util.appsection.Tab

interface AddFragmentView : BaseMainView, Tab {
    fun replacePriority(priorityTag: String)
}