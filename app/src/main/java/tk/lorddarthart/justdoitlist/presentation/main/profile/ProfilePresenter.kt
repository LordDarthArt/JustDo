package tk.lorddarthart.justdoitlist.presentation.main.profile

import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.presentation.main.profile.ProfileFragmentView
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant

@InjectViewState
class ProfilePresenter : BaseFragmentPresenter<ProfileFragmentView>() {
    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        viewState.logOut()
    }
}