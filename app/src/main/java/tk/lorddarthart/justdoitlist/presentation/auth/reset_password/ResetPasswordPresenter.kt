package tk.lorddarthart.justdoitlist.presentation.auth.reset_password

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.ActionCodeSettings
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragmentPresenter

@InjectViewState
class ResetPasswordPresenter: BaseFragmentPresenter<ResetPasswordFragmentView>() {
    val actionCodeSettings: ActionCodeSettings by lazy {
        ActionCodeSettings.newBuilder()
                .setUrl("https://tk-lorddarthart-justdo.firebaseapp.com")
                .setAndroidPackageName(JustDoItListApp.INSTANCE.packageName, false, null)
                .build()
    }
}