package tk.lorddarthart.justdoitlist.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.databinding.FragmentAuthBinding
import tk.lorddarthart.justdoitlist.model.pojo.auth.Link
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant
import tk.lorddarthart.justdoitlist.util.customobjects.CustomSpannableString
import tk.lorddarthart.justdoitlist.util.helper.setTextDisabled
import tk.lorddarthart.justdoitlist.util.helper.setTextEnabled

class AuthFragment : BaseAuthFragment(), AuthFragmentView {
    @InjectPresenter lateinit var authPresenter: AuthPresenter

    private val agreementLinks: List<Link> by lazy {
        listOf(
            Link(getString(R.string.terms_and_conditions), fragmentBundle = Bundle().apply { putString(ArgumentsKeysConstant.ACTIVITY, DefaultValuesConstant.TERMS_AND_CONDITIONS) }),
            Link(getString(R.string.privacy_policy), fragmentBundle = Bundle().apply { putString(ArgumentsKeysConstant.ACTIVITY, DefaultValuesConstant.PRIVACY_POLICY) })
        )
    }

    private val agreementText: String by lazy {
        String.format(getString(R.string.agree), getString(R.string.terms_and_conditions), getString(R.string.privacy_policy))
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentAuthBinding) {
            buttonSignIn.setOnClickListener { authPresenter.moveToSignIn() }
            buttonSignUp.setOnClickListener { authPresenter.moveToSignUp() }
        }
    }

    override fun start() {
        with(fragmentBinding as FragmentAuthBinding) {
            authPresenter.moveToSignIn()
            agreementTopSentence.text = agreementText

            activity.setSupportActionBar(authHead)
            authHeadTitle.text = getString(R.string.authentication)
        }
        setSpan()
    }

    override fun setSpan() {
        with(fragmentBinding as FragmentAuthBinding) {
            agreementTopSentence.text = CustomSpannableString(
                agreementTopSentence.text.toString(),
                agreementLinks,
                agreementTopSentence
            ).apply {
                createForAuthScreen(activity, this@AuthFragment)
            }
        }
    }

    override fun showSignIn() {
        with(fragmentBinding as FragmentAuthBinding) {
            buttonSignUp.setTextDisabled()
            buttonSignIn.setTextEnabled()
        }
        router.moveToSignIn()
    }

    override fun showSignUp() {
        with(fragmentBinding as FragmentAuthBinding) {
            buttonSignUp.setTextEnabled()
            buttonSignIn.setTextDisabled()
        }
        router.moveToSignUp()
    }

    fun openAgreements(fragmentBundle: Bundle?) {
        router.showAgreement(fragmentBundle)
    }
}
