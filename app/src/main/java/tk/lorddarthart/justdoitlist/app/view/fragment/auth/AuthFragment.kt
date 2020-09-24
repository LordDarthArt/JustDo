package tk.lorddarthart.justdoitlist.app.view.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.AuthPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.additional_info.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAuthBinding
import tk.lorddarthart.justdoitlist.util.customobjects.CustomSpannableString
import tk.lorddarthart.justdoitlist.util.helper.setTextDisabled
import tk.lorddarthart.justdoitlist.util.helper.setTextEnabled
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class AuthFragment : BaseAuthFragment(), AuthFragmentView {
    @InjectPresenter lateinit var authPresenter: AuthPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentAuthBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentAuthBinding) {
            buttonSignIn.setOnClickListener {
                if (navUtils.authNavigator.backStack.last() != SignInFragment::class.java.simpleName) {
                    authPresenter.moveToSignIn()
                    buttonSignUp.setTextDisabled()
                    buttonSignIn.setTextEnabled()
                }
            }
            buttonSignUp.setOnClickListener {
                if (navUtils.authNavigator.backStack.last() != SignUpFragment::class.java.simpleName) {
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
            agreementBottomSentence.text = authPresenter.agreementText

            activity.setSupportActionBar(authHead)
            authHeadTitle.text = getString(R.string.progress_auth)
            activity.supportActionBar?.let {
                it.show()
                it.elevation = 0f
            }
        }
        setSpan()
    }

    private fun setSpan() {
        with(fragmentBinding as FragmentAuthBinding) {
            agreementBottomSentence.text = CustomSpannableString(
                    agreementBottomSentence.text.toString(),
                    authPresenter.agreementLinks,
                    agreementBottomSentence
            ).apply {
                createForAuthScreen(activity, this@AuthFragment)
            }
        }
    }

    override fun showSignIn() {
        navUtils.authNavigator.navigate(SignInFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    override fun showSignUp() {
        navUtils.authNavigator.navigate(SignUpFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    fun openAgreements(fragmentBundle: Bundle?) {
        navUtils.baseNavigator.navigate(AdditionalInfoFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim, fragmentBundle)
    }
}
