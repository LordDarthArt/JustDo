package tk.lorddarthart.justdoitlist.util.navigation

import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.MainFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.error.ErrorFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.no_to_do.NoToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragment
import tk.lorddarthart.smartnavigation.SmartNavigator
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Singleton

/** Singleton object containing all the tools you need to work with navigation. */
@Singleton
class NavUtils(
        /** Base application's navigator for navigating between fragments in [R.id.fragment_base_container] */
        var baseNavigator: SmartNavigator,
        /** Application's navigator for navigating between fragments in [R.id.fragment_main_container] */
        var mainNavigator: SmartNavigator,
        /** Application's navigator for navigating between fragments in [R.id.fragment_enter] */
        var authNavigator: SmartNavigator
) {
    fun moveNextAfterSplash() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            baseNavigator.navigate(AuthFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        } else {
            baseNavigator.navigate(MainFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        }
    }

    fun showLoading() {
        if (LoadingFragment.INSTANCE == null) {
            LoadingFragment.INSTANCE = LoadingFragment()
        }
        mainNavigator.navigate(LoadingFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    fun moveToToDoList() {
        if (ToDoFragment.INSTANCE == null) {
            ToDoFragment.INSTANCE = ToDoFragment()
        }
        mainNavigator.navigate(ToDoFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    fun moveToProfile() {
        if (ProfileFragment.INSTANCE == null) {
            ProfileFragment.INSTANCE = ProfileFragment()
        }
        mainNavigator.navigate(ProfileFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    fun moveToError() {
        if (ErrorFragment.INSTANCE == null) {
            ErrorFragment.INSTANCE = ErrorFragment()
        }
        mainNavigator.navigate(ErrorFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    fun moveToNoToDos() {
        if (NoToDoFragment.INSTANCE == null) {
            NoToDoFragment.INSTANCE = NoToDoFragment()
        }
        mainNavigator.navigate(NoToDoFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    fun openAddFragment() {
        mainNavigator.navigate(AddFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.SlideAnim)
    }

    fun openSplash() {
        baseNavigator.navigate(SplashFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.NoAnim)
    }

    fun init(fragmentManager: FragmentManager) {
        baseNavigator.init(fragmentManager)
        mainNavigator.init(fragmentManager)
        authNavigator.init(fragmentManager)
    }

    fun removeFromBackStack(backStackKey: String) {
        baseNavigator.removeFromBackStack(backStackKey)
        mainNavigator.removeFromBackStack(backStackKey)
        authNavigator.removeFromBackStack(backStackKey)
    }
}