package tk.lorddarthart.justdoitlist.app.view.fragment.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.AuthPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAuthBinding
import tk.lorddarthart.justdoitlist.util.custom_objects.CustomSpannableString
import tk.lorddarthart.justdoitlist.util.helper.setTextDisabled
import tk.lorddarthart.justdoitlist.util.helper.setTextEnabled
import tk.lorddarthart.justdoitlist.util.navigation.CustomNavigator
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.AuthNavigator

class AuthFragment : BaseAuthFragment(), AuthFragmentView {
    @InjectPresenter
    lateinit var authPresenter: AuthPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AuthNavigator = CustomNavigator(R.id.fragment_enter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentAuthBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentAuthBinding) {
            buttonSignIn.setOnClickListener {
                if (AuthNavigator.currentFragment !is SignInFragment) {
                    authPresenter.moveToSignIn()
                    buttonSignUp.setTextDisabled()
                    buttonSignIn.setTextEnabled()
                }
            }
            buttonSignUp.setOnClickListener {
                if (AuthNavigator.currentFragment !is SignUpFragment) {
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
                createForAuthScreen()
            }
        }
    }
}
