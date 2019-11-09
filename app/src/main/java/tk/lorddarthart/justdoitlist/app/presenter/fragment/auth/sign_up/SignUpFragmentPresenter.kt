package tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.sign_up

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragmentView

@InjectViewState
class SignUpFragmentPresenter : BaseFragmentPresenter<SignUpFragmentView>() {
    var emailString: String? = null
    var passwordString: String? = null
    var confirmPasswordString: String? = null
}