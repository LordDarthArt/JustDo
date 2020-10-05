package tk.lorddarthart.justdoitlist.bussiness.main.profile

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.main.base.BaseMainPresenter
import tk.lorddarthart.justdoitlist.view.main.home.profile.ProfileFragmentView
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : BaseMainPresenter<ProfileFragmentView>() {
    @Inject lateinit var auth: FirebaseAuth

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        viewState.logOut()
    }
}