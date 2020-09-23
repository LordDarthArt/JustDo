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
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject

@InjectViewState
class AuthPresenter : BaseAuthPresenter<AuthFragmentView>() {
    @Inject lateinit var navUtils: NavUtils
    val agreementLinks = mutableListOf(
            Link(App.INSTANCE.getString(R.string.terms_and_conditions), fragmentBundle = Bundle().apply { putString(ACTIVITY, TERMS_AND_CONDITIONS) }),
            Link(App.INSTANCE.getString(R.string.privacy_policy), fragmentBundle = Bundle().apply { putString(ACTIVITY, PRIVACY_POLICY) })
    )

    val agreementText = String.format(App.INSTANCE.getString(R.string.terms_and_conditions_and_privacy_policy), App.INSTANCE.getString(R.string.terms_and_conditions), App.INSTANCE.getString(R.string.privacy_policy))

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignIn() {
        navUtils.authNavigator?.navigate(SignInFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    /** Function which shows [SignUpFragment] inside [AuthFragment].  */
    fun moveToSignUp() {
        navUtils.authNavigator?.navigate(SignUpFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }
}