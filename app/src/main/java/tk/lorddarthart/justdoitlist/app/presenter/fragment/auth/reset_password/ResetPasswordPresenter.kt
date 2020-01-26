package tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.reset_password

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.ActionCodeSettings
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.reset_password.ResetPasswordFragmentView

@InjectViewState
class ResetPasswordPresenter: BaseFragmentPresenter<ResetPasswordFragmentView>() {
    val actionCodeSettings: ActionCodeSettings by lazy {
        ActionCodeSettings.newBuilder()
                .setUrl("https://tk-lorddarthart-justdo.firebaseapp.com")
                .setAndroidPackageName(App.instance.packageName, false, null)
                .build()
    }
}