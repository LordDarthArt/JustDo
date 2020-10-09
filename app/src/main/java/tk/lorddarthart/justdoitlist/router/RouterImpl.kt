package tk.lorddarthart.justdoitlist.router

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.view.auth.AuthFragment
import tk.lorddarthart.justdoitlist.view.auth.additionalinfo.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.view.auth.resetpassword.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.view.auth.signin.SignInFragment
import tk.lorddarthart.justdoitlist.view.auth.signup.SignUpFragment
import tk.lorddarthart.justdoitlist.view.main.home.HomeFragment
import tk.lorddarthart.justdoitlist.view.main.home.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.view.main.home.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.view.main.add.AddFragment
import tk.lorddarthart.justdoitlist.view.splash.SplashFragment
import tk.lorddarthart.smartnavigation.SmartNavigator
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class RouterImpl(
    override var baseNavigator: SmartNavigator,
    override var homeNavigator: SmartNavigator,
    override var authNavigator: SmartNavigator
): Router {
    override fun openNextAfterSplash() {
        if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
            baseNavigator.navigate(HomeFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        } else {
            baseNavigator.navigate(AuthFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }
    }

    override fun moveToToDoList() {
        homeNavigator.navigate(ToDoFragment(), NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToProfile() {
        homeNavigator.navigate(ProfileFragment(), NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun showAddFragment() {
        baseNavigator.navigate(AddFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.SlideAnim)
    }

    override fun openSplash() {
        baseNavigator.navigate(SplashFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.NoAnim)
    }

    override fun moveToSignIn() {
        authNavigator.navigate(SignInFragment(), NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToSignUp() {
        authNavigator.navigate(SignUpFragment(), NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun showAgreement(fragmentBundle: Bundle?) {
        baseNavigator.navigate(AdditionalInfoFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim, fragmentBundle)
    }

    override fun showResetPassword(bundle: Bundle) {
        baseNavigator.navigate(ResetPasswordFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.SlideAnim, bundle)
    }

    override fun clearBackStack() {
        baseNavigator.backStack.clear()
        homeNavigator.backStack.clear()
        authNavigator.backStack.clear()
    }
}