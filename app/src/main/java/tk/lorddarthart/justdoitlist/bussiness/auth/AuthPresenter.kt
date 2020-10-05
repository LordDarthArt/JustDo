package tk.lorddarthart.justdoitlist.bussiness.auth

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.view.auth.AuthFragmentView
import tk.lorddarthart.justdoitlist.view.auth.signup.SignUpFragment

@InjectViewState
class AuthPresenter : BaseAuthPresenter<AuthFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignIn() {
        viewState.showSignIn()
    }

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignUp() {
        viewState.showSignUp()
    }
}