package tk.lorddarthart.justdoitlist.presentation.auth.sign_up

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.presentation.auth.AuthFragment
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSignUpBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.customobjects.SimpleTextWatcher
import tk.lorddarthart.justdoitlist.util.helper.longSnackbar
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject

class SignUpFragment : BaseAuthFragment(), SignUpFragmentView {
    @Inject lateinit var auth: FirebaseAuth

    @InjectPresenter lateinit var signUpPresenter: SignUpPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentSignUpBinding.inflate(inflater, container, false)
    }

    override fun start() {
        with (fragmentBinding as FragmentSignUpBinding) {
            arguments?.let { arguments ->
                if (arguments.containsKey(IntentExtraConstNames.EMAIL)) {
                    signUpEmailInput.setText(arguments.getString(IntentExtraConstNames.EMAIL))
                }
            }
        }
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentSignUpBinding) {
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
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    sendVerificationEmail()
                                } else {
                                    fragmentBinding.root.shortSnackbar { getString(R.string.reg_fail) }
                                }
                            }
                } else {
                    with (signUpPresenter) {
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
        with (fragmentBinding as FragmentSignUpBinding) {
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
                            val fragment = AuthFragment()
                            router.baseNavigator.navigate(fragment, actionType = NavigationActionType.ReplaceAction, animType = NavigationAnimType.NoAnim)
                            fragmentBinding.root.shortSnackbar { activity.resources.getString(R.string.reg_success) }
                        } else {
                            fragmentBinding.root.shortSnackbar { activity.resources.getString(R.string.verification_problem) + " " +task.exception.toString() }
                        }
                    }
        }
    }
}
