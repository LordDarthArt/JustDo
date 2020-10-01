package tk.lorddarthart.justdoitlist.presentation.auth

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.presentation.auth.sign_up.SignUpFragment

@InjectViewState
class AuthPresenter : BaseAuthPresenter<AuthFragmentView>() {
    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignIn() {
        viewState.showSignIn()
    }

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignUp() {
        viewState.showSignUp()
    }
}