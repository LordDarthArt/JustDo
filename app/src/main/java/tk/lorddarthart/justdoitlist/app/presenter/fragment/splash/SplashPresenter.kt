package tk.lorddarthart.justdoitlist.app.presenter.fragment.splash

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.MainFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragmentView
import tk.lorddarthart.justdoitlist.util.navigation.NavAction
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

@InjectViewState
class SplashPresenter: BaseFragmentPresenter<SplashFragmentView>() {
    fun proceedNext() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            NavAction.OpenNavDestinations
        } else {
            NavUtils.baseNavigator.navigate(MainFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.SlideAnim)
        }
    }

    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser
}