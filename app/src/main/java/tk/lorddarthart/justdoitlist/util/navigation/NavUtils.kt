package tk.lorddarthart.justdoitlist.util.navigation

import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.MainFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.error.ErrorFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.no_to_do.NoToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragment
import tk.lorddarthart.smartnavigation.SmartNavigator
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject
import javax.inject.Singleton

/** Singleton object containing all the tools you need to work with navigation. */
@Singleton
class NavUtils {

    @Inject
    constructor()

    /** Base application's navigator for navigating between fragments in [R.id.fragment_base_container] */
    var baseNavigator: SmartNavigator? = null
    /** Aapplication's navigator for navigating between fragments in [R.id.fragment_main_container] */
    var mainNavigator: SmartNavigator? = null
    /** Aapplication's navigator for navigating between fragments in [R.id.fragment_enter] */
    var authNavigator: SmartNavigator? = null

    fun moveNextAfterSplash() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            baseNavigator?.navigate(AuthFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        } else {
            baseNavigator?.navigate(MainFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        }
    }

    fun showLoading() {
        mainNavigator?.navigate(LoadingFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun moveToToDoList() {
        mainNavigator?.navigate(ToDoFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun moveToProfile() {
        mainNavigator?.navigate(ProfileFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun moveToError() {
        mainNavigator?.navigate(ErrorFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun moveToNoToDos() {
        mainNavigator?.navigate(NoToDoFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun openSplash() {
        baseNavigator?.navigate(SplashFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.NoAnim)
    }
}