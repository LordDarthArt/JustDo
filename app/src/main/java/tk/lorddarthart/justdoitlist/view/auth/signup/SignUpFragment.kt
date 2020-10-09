package tk.lorddarthart.justdoitlist.view.auth.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_up_fragment.*
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.auth.signup.SignUpPresenter
import tk.lorddarthart.justdoitlist.databinding.SignUpFragmentBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.customobjects.SimpleTextWatcher
import tk.lorddarthart.justdoitlist.view.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator
import tk.lorddarthart.smartnavigation.NavigationTab
import javax.inject.Inject

class SignUpFragment : BaseAuthFragment(), NavigationTab, SignUpFragmentView {
    @InjectPresenter lateinit var signUpPresenter: SignUpPresenter

    @Inject lateinit var auth: FirebaseAuth

    override var INSTANCE: NavigationTab?
        get() { return SignUpFragment.INSTANCE }
        set(value) { SignUpFragment.INSTANCE = value }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = SignUpFragmentBinding.inflate(inflater, container, false)
    }

    override fun start() {
        JustDoItListApp.component?.inject(this)
        with(fragmentBinding as SignUpFragmentBinding) {
            arguments?.let { arguments ->
                if (arguments.containsKey(IntentExtraConstNames.EMAIL)) {
                    signUpEmailInput.setText(arguments.getString(IntentExtraConstNames.EMAIL))
                }
            }
        }
    }

    override fun initListeners() {
        with(fragmentBinding as SignUpFragmentBinding) {
            signUpEmailInput.addTextChangedListener(SimpleTextWatcher { text, _, _, _ ->
                signUpPresenter.emailString = text.toString()
            })
            signUpPasswordInput.addTextChangedListener(SimpleTextWatcher { text, _, _, _ ->
                signUpPresenter.passwordString = text.toString()
            })
            signUpConfirmPasswordInput.addTextChangedListener(SimpleTextWatcher { text, _, _, _ ->
                signUpPresenter.confirmPasswordString = text.toString()
            })
            signUpButton.setOnClickListener {
                setFieldsErrorsToNull()
                if (!signUpPresenter.emailString.isNullOrBlank() && !signUpPresenter.passwordString.isNullOrBlank()
                    && PasswordEmailValidator.isValidEmailAddress(sign_up_email_input.text.toString())
                    && PasswordEmailValidator.isValidPassword(signUpPasswordInput.text.toString())
                    && signUpPresenter.passwordString == signUpPresenter.confirmPasswordString) {
                    auth.createUserWithEmailAndPassword(signUpPresenter.emailString!!, signUpPresenter.passwordString!!)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                sendVerificationEmail()
                            } else {
                                fragmentBinding.root.shortSnackbar { getString(R.string.reg_fail) }
                            }
                        }
                } else {
                    with(signUpPresenter) {
                        when {
                            passwordString != signUpPresenter.confirmPasswordString -> signUpConfirmPasswordLayout.error = "passwords don't match"
                            !PasswordEmailValidator.isValidEmailAddress(sign_up_email_input.text.toString()) -> signUpEmailLayout.error = "email is not valid"
                            !PasswordEmailValidator.isValidPassword(sign_up_password_input.text.toString()) -> signUpPasswordLayout.error = "password is not valid"
                            emailString == "" -> signUpEmailLayout.error = "email can't be empty"
                            passwordString == "" -> signUpPasswordLayout.error = "password can't be empty"
                            confirmPasswordString == "" -> signUpConfirmPasswordLayout.error = "password confirmation can't be empty"
                        }
                    }
                }
            }
        }
    }

    private fun setFieldsErrorsToNull() {
        with(fragmentBinding as SignUpFragmentBinding) {
            signUpEmailLayout.error = null
            signUpPasswordLayout.error = null
            signUpConfirmPasswordLayout.error = null
        }
    }

    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            it.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().signOut()
                        router.openNextAfterSplash()
                        fragmentBinding.root.shortSnackbar { requireContext().resources.getString(R.string.reg_success) }
                    } else {
                        fragmentBinding.root.shortSnackbar { requireContext().resources.getString(R.string.verification_problem) + " ${task.exception.toString()}" }
                    }
                }
        }
    }

    companion object : NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
