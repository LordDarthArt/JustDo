package tk.lorddarthart.justdoitlist.presentation.auth.sign_up

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragmentPresenter

@InjectViewState
class SignUpPresenter : BaseFragmentPresenter<SignUpFragmentView>() {
    var emailString: String? = null
    var passwordString: String? = null
    var confirmPasswordString: String? = null
}