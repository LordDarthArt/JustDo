package tk.lorddarthart.justdoitlist.router

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.presentation.auth.AuthFragment
import tk.lorddarthart.justdoitlist.presentation.auth.additional_info.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.presentation.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.presentation.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.presentation.main.MainFragment
import tk.lorddarthart.justdoitlist.presentation.main.additional_view.error.ErrorFragment
import tk.lorddarthart.justdoitlist.presentation.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.presentation.main.additional_view.no_to_do.NoToDoFragment
import tk.lorddarthart.justdoitlist.presentation.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.presentation.main.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.presentation.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.presentation.splash.SplashFragment
import tk.lorddarthart.smartnavigation.SmartNavigator
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class Router(
    override var baseNavigator: SmartNavigator,
    override var mainNavigator: SmartNavigator,
    override var authNavigator: SmartNavigator
): IRouter {
    override fun openNextAfterSplash() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            baseNavigator.navigate(AuthFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        } else {
            baseNavigator.navigate(MainFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        }
    }

    override fun moveToLoading() {
        if (LoadingFragment.INSTANCE == null) {
            LoadingFragment.INSTANCE = LoadingFragment()
        }
        mainNavigator.navigate(LoadingFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToToDoList() {
        if (ToDoFragment.INSTANCE == null) {
            ToDoFragment.INSTANCE = ToDoFragment()
        }
        mainNavigator.navigate(ToDoFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToProfile() {
        if (ProfileFragment.INSTANCE == null) {
            ProfileFragment.INSTANCE = ProfileFragment()
        }
        mainNavigator.navigate(ProfileFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToError() {
        if (ErrorFragment.INSTANCE == null) {
            ErrorFragment.INSTANCE = ErrorFragment()
        }
        mainNavigator.navigate(ErrorFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToNoToDos() {
        if (NoToDoFragment.INSTANCE == null) {
            NoToDoFragment.INSTANCE = NoToDoFragment()
        }
        mainNavigator.navigate(NoToDoFragment.INSTANCE!!, NavigationActionType.ShowAction, NavigationAnimType.FadeAnim)
    }

    override fun openAddFragment() {
        mainNavigator.navigate(AddFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.SlideAnim)
    }

    override fun openSplash() {
        baseNavigator.navigate(SplashFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.NoAnim)
    }

    override fun moveToSignIn() {
        authNavigator.navigate(SignInFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    override fun moveToSignUp() {
        authNavigator.navigate(SignUpFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    override fun showAgreement(fragmentBundle: Bundle?) {
        baseNavigator.navigate(AdditionalInfoFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim, fragmentBundle)
    }

    override fun clearBackStack() {
        baseNavigator.backStack.clear()
        mainNavigator.backStack.clear()
        authNavigator.backStack.clear()
    }
}