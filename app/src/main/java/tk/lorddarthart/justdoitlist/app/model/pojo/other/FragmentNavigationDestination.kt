package tk.lorddarthart.justdoitlist.app.model.pojo.other

import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.util.navigation.navigatable.Destination

class FragmentNavigationDestination(
        override val destinationFragment: BaseFragment,
        override var isInitialized: Boolean = false
) : Destination