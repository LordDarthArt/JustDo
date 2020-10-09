package tk.lorddarthart.justdoitlist.router

import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.smartnavigation.SmartNavigator

/** Tool that performs the app's navigation. */
interface Router {
    /** Application's navigator for navigating between fragments in [R.id.base_container]. */
    var baseNavigator: SmartNavigator
    /** Application's navigator for navigating between fragments in [R.id.fragment_main_container]. */
    var homeNavigator: SmartNavigator
    /** Application's navigator for navigating between fragments in [R.id.auth_container]. */
    var authNavigator: SmartNavigator

    /** Opens the agreement fragment user is trying to access using [baseNavigator] */
    fun showAgreement(fragmentBundle: Bundle?)
    /** Opens to-do list tab using [homeNavigator]. */
    fun moveToToDoList()
    /** Opens profile tab using [homeNavigator]. */
    fun moveToProfile()
    /** Opens sign-in tab using [authNavigator]. */
    fun moveToSignIn()
    /** Opens sign-up tab using [authNavigator]. */
    fun moveToSignUp()
    /** Opens next fragment after splash using [baseNavigator]. */
    fun openNextAfterSplash()
    /** Opens add to-do fragment using [baseNavigator]. */
    fun showAddFragment()
    /** Opens reset password fragment using [baseNavigator]. */
    fun showResetPassword(bundle: Bundle)
    /** Opens splash fragment using [baseNavigator]. */
    fun openSplash()

    /** Clears every navigator's custom backStack. */
    fun clearBackStack()
}