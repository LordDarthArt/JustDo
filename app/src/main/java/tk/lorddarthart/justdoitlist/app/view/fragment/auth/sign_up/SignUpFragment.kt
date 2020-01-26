package tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.sign_up.SignUpPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSignUpBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.custom_objects.SimpleTextWatcher
import tk.lorddarthart.justdoitlist.util.helper.clickHidePass
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator

class SignUpFragment : BaseAuthFragment(), SignUpFragmentView {
    @InjectPresenter
    lateinit var signUpPresenter: SignUpPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun start() {
        with (fragmentBinding as FragmentSignUpBinding) {
            signUpHidePasswordIcon.tag = R.drawable.ic_eye_unvisible
            signUpHideConfirmPasswordIcon.tag = R.drawable.ic_eye_unvisible
            arguments?.let { arguments ->
                if (arguments.containsKey(IntentExtraConstNames.EMAIL)) {
                    signUpEmailInput.setText(arguments.getString(IntentExtraConstNames.EMAIL))
                }
            }
        }
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentSignUpBinding) {
            signUpHidePasswordIcon.setOnClickListener {
                signUpHidePasswordIcon.clickHidePass(signUpPasswordInput)
            }
            signUpHideConfirmPasswordIcon.setOnClickListener {
                signUpHideConfirmPasswordIcon.clickHidePass(signUpConfirmPasswordInput)
            }
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
                    activity.baseActivityPresenter.auth.createUserWithEmailAndPassword(signUpPresenter.emailString!!, signUpPresenter.passwordString!!)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    sendVerificationEmail()
                                } else {
                                    Snackbar.make(activity.findViewById<View>(android.R.id.content), getString(R.string.reg_fail), Snackbar.LENGTH_SHORT).show()
                                }
                            }
                } else {
                    when {
                        signUpPresenter.passwordString != signUpPresenter.confirmPasswordString -> signUpConfirmPasswordLayout.error = "passwords don't match"
                        !PasswordEmailValidator.isValidEmailAddress(sign_up_email_input.text.toString()) -> signUpEmailLayout.error = "email is not valid"
                        !PasswordEmailValidator.isValidPassword(sign_up_password_input.text.toString()) -> signUpPasswordLayout.error = "password is not valid"
                        signUpPresenter.emailString == "" -> signUpEmailLayout.error = "email can't be empty"
                        signUpPresenter.passwordString == "" -> signUpPasswordLayout.error = "password can't be empty"
                        signUpPresenter.confirmPasswordString == "" -> signUpConfirmPasswordLayout.error = "password confirmation can't be empty"
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
                            activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_base, fragment).commit()
                            Snackbar.make(activity.findViewById<View>(android.R.id.content), activity.resources.getString(R.string.reg_success), Snackbar.LENGTH_SHORT).show()
                        } else {
                            Snackbar.make(activity.findViewById<View>(android.R.id.content), activity.resources.getString(R.string.verification_problem) + " " +task.exception.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
        }
    }
}
