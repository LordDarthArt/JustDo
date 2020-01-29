package tk.lorddarthart.justdoitlist.util.navigation


import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.error.ErrorFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.no_to_do.NoToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.util.navigation.navigatable.Destination
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

object NavAction {
    fun performTransaction(
            navigator: CustomNavigator = CustomNavigator(R.id.fragment_base),
            destination: Destination,
            actionType: NavigationActionType,
            animationType: NavigationAnimType,
            bundle: Bundle? = null
    ) {
        navigator.navigate(destination, actionType, animationType, bundle)
    }

    object ShowNavDestinations {
        fun showInBase(destination: Destination, bundle: Bundle? = null) {
            show(destination, NavUtils.baseNavigator, bundle)
        }

        fun showTab(destination: Destination, navigator: CustomNavigator, bundle: Bundle? = null) {
            show(destination, navigator, bundle)
        }

        fun show(destination: Destination, navigator: CustomNavigator, bundle: Bundle? = null) {
            if (destination.isInitialized) { navigator.navigate(destination, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim, bundle) } else { navigator.navigate(destination, NavigationActionType.JustAddAction, NavigationAnimType.FadeAnim, bundle) }
        }

        fun showSignIn() {
            showTab()
        }

        fun showSignUp() {

        }
    }

    object OpenNavDestinations {
        fun openInBase(destination: Destination, bundle: Bundle? = null) {
            NavUtils.baseNavigator.navigate(destination, NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim, bundle)
        }

        fun openTab(destination: Destination, navigator: CustomNavigator, bundle: Bundle? = null) {
            navigator.navigate(destination, NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim, bundle)
        }

        fun openLoading() {
            NavUtils.mainNavigator.navigate(LoadingFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }

        fun openToDoList() {
            NavUtils.mainNavigator.navigate(ToDoFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }

        fun openProfile() {
            NavUtils.mainNavigator.navigate(ProfileFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }

        fun openError() {
            NavUtils.mainNavigator.navigate(ErrorFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }

        fun openNoToDos() {
            NavUtils.mainNavigator.navigate(NoToDoFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }

        fun openSplash() {
            openInBase()
        }
    }
}