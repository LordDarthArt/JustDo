package tk.lorddarthart.justdoitlist.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.presentation.splash.SplashFragmentView

@InjectViewState
class SplashPresenter: BaseFragmentPresenter<SplashFragmentView>() {
    private val currentUser: FirebaseUser? by lazy {
        FirebaseAuth.getInstance().currentUser
    }
}