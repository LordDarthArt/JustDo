package tk.lorddarthart.justdoitlist.presentation.auth

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.App
import tk.lorddarthart.justdoitlist.model.pojo.auth.Link
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.presentation.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant.ACTIVITY
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant.PRIVACY_POLICY
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant.TERMS_AND_CONDITIONS
import tk.lorddarthart.justdoitlist.router.Router
import javax.inject.Inject

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