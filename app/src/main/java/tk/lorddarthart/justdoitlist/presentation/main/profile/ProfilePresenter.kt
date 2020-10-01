package tk.lorddarthart.justdoitlist.presentation.main.profile

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragmentPresenter

@InjectViewState
class ProfilePresenter : BaseFragmentPresenter<ProfileFragmentView>() {
    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        viewState.logOut()
    }
}