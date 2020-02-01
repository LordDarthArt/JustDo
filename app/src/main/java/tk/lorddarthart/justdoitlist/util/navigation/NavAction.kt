package tk.lorddarthart.justdoitlist.util.navigation

import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.util.navigation.navigatable.Destination
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType
import tk.lorddarthart.justdoitlist.util.singleton.NavigationDestination.AuthNavigationDestination

object NavAction {
    fun performTransaction(navigator: CustomNavigator = CustomNavigator(R.id.fragment_base), destination: Destination, actionType: NavigationActionType, animationType: NavigationAnimType, bundle: Bundle? = null) {
        navigator.navigate(destination, actionType, animationType, bundle)
    }

    object ShowNavAction {
        fun showTab(destination: Destination, navigator: CustomNavigator, bundle: Bundle? = null) {
            if (destination.isInitialized) { performTransaction(navigator, destination, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim, bundle) } else { performTransaction(navigator, destination, NavigationActionType.JustAddAction, NavigationAnimType.FadeAnim, bundle) }
        }

        fun show(destination: Destination, navigator: CustomNavigator, bundle: Bundle? = null) {
            performTransaction(navigator, destination, NavigationActionType.AddToBackStackAction, NavigationAnimType.SlideAnim, bundle)
        }

        fun showSignIn(bundle: Bundle? = null) {
            showTab(AuthNavigationDestination.SignInDestination, NavUtils.AuthNavigator, bundle)
        }

        fun showSignUp(bundle: Bundle? = null) {
            showTab(AuthNavigationDestination.SignUpDestination, NavUtils.AuthNavigator, bundle)
        }
    }

    object OpenNavAction {
        fun openInBase(navigator: CustomNavigator = NavUtils.BaseNavigator, fragment: BaseFragment, bundle: Bundle? = null) {
            navigator.navigate(fragment, NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim, bundle)
        }
    }
}