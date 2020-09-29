package tk.lorddarthart.justdoitlist.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.App
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.presentation.auth.additional_info.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.presentation.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.presentation.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAuthBinding
import tk.lorddarthart.justdoitlist.model.pojo.auth.Link
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant
import tk.lorddarthart.justdoitlist.util.customobjects.CustomSpannableString
import tk.lorddarthart.justdoitlist.util.helper.setTextDisabled
import tk.lorddarthart.justdoitlist.util.helper.setTextEnabled
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class AuthFragment : BaseAuthFragment(), AuthFragmentView {
    @InjectPresenter lateinit var authPresenter: AuthPresenter

    private val agreementLinks = mutableListOf(
        Link(getString(R.string.terms_and_conditions), fragmentBundle = Bundle().apply { putString(ArgumentsKeysConstant.ACTIVITY, DefaultValuesConstant.TERMS_AND_CONDITIONS) }),
        Link(getString(R.string.privacy_policy), fragmentBundle = Bundle().apply { putString(ArgumentsKeysConstant.ACTIVITY, DefaultValuesConstant.PRIVACY_POLICY) })
    )

    private val agreementText = String.format(getString(R.string.terms_and_conditions_and_privacy_policy), getString(R.string.terms_and_conditions), getString(R.string.privacy_policy))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentAuthBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentAuthBinding) {
            buttonSignIn.setOnClickListener {
                if (router.authNavigator.backStack.last() != SignInFragment::class.java.simpleName) {
                    authPresenter.moveToSignIn()
                    buttonSignUp.setTextDisabled()
                    buttonSignIn.setTextEnabled()
                }
            }
            buttonSignUp.setOnClickListener {
                if (router.authNavigator.backStack.last() != SignUpFragment::class.java.simpleName) {
                    authPresenter.moveToSignUp()
                    buttonSignUp.setTextEnabled()
                    buttonSignIn.setTextDisabled()
                }
            }
        }
    }

    override fun start() {
        with(fragmentBinding as FragmentAuthBinding) {
            authPresenter.moveToSignIn()
            agreementBottomSentence.text = agreementText

            activity.setSupportActionBar(authHead)
            authHeadTitle.text = getString(R.string.authentication)
        }
        setSpan()
    }

    private fun setSpan() {
        with(fragmentBinding as FragmentAuthBinding) {
            agreementBottomSentence.text = CustomSpannableString(
                    agreementBottomSentence.text.toString(),
                    agreementLinks,
                    agreementBottomSentence
            ).apply {
                createForAuthScreen(activity, this@AuthFragment)
            }
        }
    }

    override fun showSignIn() {
        router.authNavigator.navigate(SignInFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    override fun showSignUp() {
        router.authNavigator.navigate(SignUpFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun openAgreements(fragmentBundle: Bundle?) {
        router.baseNavigator.navigate(AdditionalInfoFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim, fragmentBundle)
    }
}
