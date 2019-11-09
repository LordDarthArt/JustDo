package tk.lorddarthart.justdoitlist.app.view.fragment.auth

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.additional_info.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAuthBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstValues
import tk.lorddarthart.justdoitlist.util.helper.setTextDisabled
import tk.lorddarthart.justdoitlist.util.helper.setTextEnabled
import java.util.regex.Pattern

class AuthFragment : BaseFragment(), AuthFragmentView {
    private lateinit var authBinding: FragmentAuthBinding
    private lateinit var fragment: Fragment

    private val currentAuthFragment: Fragment?
        get() = activity.supportFragmentManager.findFragmentById(R.id.fragment_enter)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        authBinding = FragmentAuthBinding.inflate(
                inflater,
                container,
                false
        )

        initialization()

        return authBinding.root
    }

    fun initialization() {
        start()
        initListeners()
        setSpan()
    }

    private fun start() {
        activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_enter, SignInFragment()).commit()
        if (activity.intent.hasExtra(IntentExtraConstNames.showExtraNotifications)) {
            when (activity.intent.getStringExtra(IntentExtraConstNames.showExtraNotifications)) {
                IntentExtraConstValues.mPasswordResetSuccessful -> {
                    Snackbar.make(activity.findViewById(android.R.id.content), "Password reset instructions have been sent. Please check your email", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        authBinding.agreementBottomSentence.text = String.format(authBinding.agreementBottomSentence.text.toString(), getString(R.string.terms_and_conditions), getString(R.string.privacy_policy))
    }

    private fun initListeners() {
        authBinding.buttonSignIn.setOnClickListener {
            if (currentAuthFragment !is SignInFragment) {
                fragment = SignInFragment()
                val bundle = Bundle()
                if (activity.supportFragmentManager.fragments[1].view?.sign_up_email_input?.text.toString() != "") {
                    bundle.putString(IntentExtraConstNames.email, activity.supportFragmentManager.fragments[1].view?.sign_up_email_input?.text.toString())
                }
                fragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_enter, fragment).commit()
                authBinding.buttonSignUp.setTextDisabled()
                authBinding.buttonSignIn.setTextEnabled()
            }
        }
        authBinding.buttonSignUp.setOnClickListener {
            if (currentAuthFragment !is SignUpFragment) {
                fragment = SignUpFragment()
                val bundle = Bundle()
                if (activity.supportFragmentManager.fragments[1].view?.sign_in_email_input?.text.toString() != "") {
                    bundle.putString(IntentExtraConstNames.email, activity.supportFragmentManager.fragments[1].view?.sign_in_email_input?.text.toString())
                }
                fragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_enter, fragment).commit()
                authBinding.buttonSignUp.setTextEnabled()
                authBinding.buttonSignIn.setTextDisabled()
            }
        }
    }

    private fun setSpan() {
        authBinding.agreementBottomSentence.text = SpannableString(authBinding.agreementBottomSentence.text).apply {
            val termsAndConditions = getString(R.string.terms_and_conditions)
            val privacyPolicy = getString(R.string.privacy_policy)

            val termsAndConditionsPattern = Pattern.compile(termsAndConditions)
            val privacyPolicyPattern = Pattern.compile(privacyPolicy)

            val termsAndConditionsMatcher = termsAndConditionsPattern.matcher(this)
            val privacyPolicyMatcher = privacyPolicyPattern.matcher(this)

            val termsAndConditionsClickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(textView: View) {
                    fragment = AdditionalInfoFragment()
                    val bundle = Bundle()
                    bundle.putString(IntentExtraConstNames.activity, IntentExtraConstValues.mTermsConditions)
                    fragment.arguments = bundle
                    activity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragment).addToBackStack(null).commit()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = ContextCompat.getColor(App.instance, R.color.textColor)
                }
            }

            val privacyPolicyClickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    fragment = AdditionalInfoFragment()
                    val bundle = Bundle()
                    bundle.putString(IntentExtraConstNames.activity, IntentExtraConstValues.mPrivacyPolicy)
                    fragment.arguments = bundle
                    activity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragment).addToBackStack(null).commit()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = ContextCompat.getColor(App.instance, R.color.textColor)
                }
            }

            while (termsAndConditionsMatcher.find()) {
                setSpan(termsAndConditionsClickableSpan, termsAndConditionsMatcher.start(), termsAndConditionsMatcher.end(), SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            while (privacyPolicyMatcher.find()) {
                setSpan(privacyPolicyClickableSpan, privacyPolicyMatcher.start(), privacyPolicyMatcher.end(), SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        authBinding.agreementBottomSentence.movementMethod = LinkMovementMethod.getInstance();
    }
}
