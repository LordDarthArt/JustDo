package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add

import tk.lorddarthart.justdoitlist.app.view.fragment.main.base.BaseMainView
import tk.lorddarthart.justdoitlist.util.app_section.Tab

interface AddFragmentView : BaseMainView, Tab {
    fun replacePriority(priorityTag: String)
}