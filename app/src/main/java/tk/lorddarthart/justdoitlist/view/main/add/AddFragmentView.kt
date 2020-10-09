package tk.lorddarthart.justdoitlist.view.main.add

import tk.lorddarthart.justdoitlist.view.main.base.BaseMainView
import tk.lorddarthart.justdoitlist.util.appsection.Tab

interface AddFragmentView : BaseMainView, Tab {
    fun replacePriority(priorityTag: String)
}