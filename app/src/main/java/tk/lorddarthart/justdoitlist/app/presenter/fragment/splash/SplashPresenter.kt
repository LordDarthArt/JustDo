package tk.lorddarthart.justdoitlist.app.presenter.fragment.splash

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragmentView

@InjectViewState
class SplashPresenter: BaseFragmentPresenter<SplashFragmentView>() {
    private val currentUser: FirebaseUser? by lazy {
        FirebaseAuth.getInstance().currentUser
    }
}