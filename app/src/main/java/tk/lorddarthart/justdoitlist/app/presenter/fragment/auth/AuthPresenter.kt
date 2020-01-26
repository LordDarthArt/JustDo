package tk.lorddarthart.justdoitlist.app.presenter.fragment.auth

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.model.pojo.auth.Link
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragmentView
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant.ACTIVITY
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant.PRIVACY_POLICY
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant.TERMS_AND_CONDITIONS
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.authNavigator
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

@InjectViewState
class AuthPresenter : BaseAuthPresenter<AuthFragmentView>() {
    val agreementLinks = mutableListOf(
            Link(App.instance.getString(R.string.terms_and_conditions), fragmentBundle = Bundle().apply { putString(ACTIVITY, TERMS_AND_CONDITIONS) }),
            Link(App.instance.getString(R.string.privacy_policy), fragmentBundle = Bundle().apply { putString(ACTIVITY, PRIVACY_POLICY) })
    )

    val agreementText = String.format(App.instance.getString(R.string.terms_and_conditions_and_privacy_policy), App.instance.getString(R.string.terms_and_conditions), App.instance.getString(R.string.privacy_policy))

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignIn() {
        authNavigator.navigate(SignInFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignUp() {
        authNavigator.navigate(SignUpFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }
}