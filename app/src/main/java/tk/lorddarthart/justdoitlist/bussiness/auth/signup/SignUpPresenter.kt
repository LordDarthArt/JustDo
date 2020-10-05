package tk.lorddarthart.justdoitlist.bussiness.auth.signup

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.view.auth.signup.SignUpFragmentView
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter

@InjectViewState
class SignUpPresenter : BaseAuthPresenter<SignUpFragmentView>() {
    var emailString: String? = null
    var passwordString: String? = null
    var confirmPasswordString: String? = null

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}