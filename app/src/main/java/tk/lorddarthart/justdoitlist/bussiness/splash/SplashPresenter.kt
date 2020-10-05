package tk.lorddarthart.justdoitlist.bussiness.splash

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.view.splash.SplashFragmentView

@InjectViewState
class SplashPresenter: BaseFragmentPresenter<SplashFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}