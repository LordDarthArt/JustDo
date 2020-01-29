package tk.lorddarthart.justdoitlist.util.navigation

import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.MainFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.error.ErrorFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.no_to_do.NoToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragment
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

/** Singleton object containing all the tools you need to work with navigation. */
object NavUtils {
    /** Base application's navigator for navigating between fragments in [R.id.fragment_base] */
    lateinit var baseNavigator: CustomNavigator
    /** Aapplication's navigator for navigating between fragments in [R.id.fragment_main_container] */
    lateinit var mainNavigator: CustomNavigator
    /** Aapplication's navigator for navigating between fragments in [R.id.fragment_enter] */
    lateinit var authNavigator: CustomNavigator

    lateinit var fragmentManager: FragmentManager

    var currentFragment: BaseFragment? = null
}