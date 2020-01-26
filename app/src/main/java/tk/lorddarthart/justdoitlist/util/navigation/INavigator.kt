package tk.lorddarthart.justdoitlist.util.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

interface INavigator {
    val currentFragment: BaseFragment?

    fun navigate(targetFragment: BaseFragment, actionType: NavigationActionType, animType: NavigationAnimType, arguments: Bundle? = null)
}