package tk.lorddarthart.justdoitlist.view.main.base

import androidx.appcompat.widget.Toolbar
import tk.lorddarthart.justdoitlist.util.appsection.Tab

abstract class BaseMainTabFragment : BaseMainFragment(), Tab {
    abstract fun setupNavBar(toolbar: Toolbar)
}