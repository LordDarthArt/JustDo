package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add

import tk.lorddarthart.justdoitlist.app.view.fragment.main.base.BaseMainView
import tk.lorddarthart.justdoitlist.util.markers.other.Tab

interface AddFragmentView : BaseMainView, Tab {
    fun replacePriority(priorityTag: String)
}