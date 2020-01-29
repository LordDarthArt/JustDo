package tk.lorddarthart.justdoitlist.util.navigation.navigatable

import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment

interface Destination {
    val destinationFragment: BaseFragment
    var isInitialized: Boolean
}