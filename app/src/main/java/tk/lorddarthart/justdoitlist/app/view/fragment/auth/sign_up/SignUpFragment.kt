package tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.sign_up.SignUpFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSignUpBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.helper.clickHidePass
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator

class SignUpFragment : BaseFragment(), SignUpFragmentView {
    private lateinit var signUpFragmentBinding: FragmentSignUpBinding

    @InjectPresenter
    lateinit var signUpFragmentPresenter: SignUpFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        signUpFragmentBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        initialization()

        return signUpFragmentBinding.root
    }

    fun initialization() {
        start()
        initListeners()
    }

    private fun start() {
        signUpFragmentBinding.signUpHidePasswordIcon.tag = R.drawable.ic_eye_unvisible
        signUpFragmentBinding.signUpHideConfirmPasswordIcon.tag = R.drawable.ic_eye_unvisible
        arguments?.let { arguments ->
            if (arguments.containsKey(IntentExtraConstNames.email)) {
                signUpFragmentBinding.signUpEmailInput.setText(arguments.getString(IntentExtraConstNames.email))
            }
        }
    }

    private fun initListeners() {
        signUpFragmentBinding.signUpHidePasswordIcon.setOnClickListener {
            signUpFragmentBinding.signUpHidePasswordIcon.clickHidePass(signUpFragmentBinding.signUpPasswordInput)
        }
        signUpFragmentBinding.signUpHideConfirmPasswordIcon.setOnClickListener {
            signUpFragmentBinding.signUpHideConfirmPasswordIcon.clickHidePass(signUpFragmentBinding.signUpConfirmPasswordInput)
        }
        signUpFragmentBinding.signUpEmailInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpFragmentPresenter.emailString = s.toString()
            }
        })
        signUpFragmentBinding.signUpPasswordInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpFragmentPresenter.passwordString = s.toString()
            }
        })
        signUpFragmentBinding.signUpConfirmPasswordInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpFragmentPresenter.confirmPasswordString = s.toString()
            }
        })
        signUpFragmentBinding.signUpButton.setOnClickListener {
            setFieldsErrorsToNull()
            if (!signUpFragmentPresenter.emailString.isNullOrBlank() && !signUpFragmentPresenter.passwordString.isNullOrBlank()
                    && PasswordEmailValidator.isValidEmailAddress(sign_up_email_input.text.toString())
                    && PasswordEmailValidator.isValidPassword(signUpFragmentBinding.signUpPasswordInput.text.toString())
                    && signUpFragmentPresenter.passwordString == signUpFragmentPresenter.confirmPasswordString) {
                activity.baseActivityPresenter.auth.createUserWithEmailAndPassword(signUpFragmentPresenter.emailString!!, signUpFragmentPresenter.passwordString!!)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                sendVerificationEmail()
                            } else {
                                Snackbar.make(activity.findViewById<View>(android.R.id.content), getString(R.string.reg_fail), Snackbar.LENGTH_SHORT).show()
                            }
                        }
            } else {
                when {
                    signUpFragmentPresenter.passwordString != signUpFragmentPresenter.confirmPasswordString -> signUpFragmentBinding.signUpConfirmPasswordLayout.error = "passwords don't match"
                    !PasswordEmailValidator.isValidEmailAddress(sign_up_email_input.text.toString()) -> signUpFragmentBinding.signUpEmailLayout.error = "email is not valid"
                    !PasswordEmailValidator.isValidPassword(sign_up_password_input.text.toString()) -> signUpFragmentBinding.signUpPasswordLayout.error = "password is not valid"
                    signUpFragmentPresenter.emailString == "" -> signUpFragmentBinding.signUpEmailLayout.error = "email can't be empty"
                    signUpFragmentPresenter.passwordString == "" -> signUpFragmentBinding.signUpPasswordLayout.error = "password can't be empty"
                    signUpFragmentPresenter.confirmPasswordString == "" -> signUpFragmentBinding.signUpConfirmPasswordLayout.error = "password confirmation can't be empty"
                }
            }
        }
    }

    fun setFieldsErrorsToNull() {
        with (signUpFragmentBinding) {
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
                            activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit()
                            Snackbar.make(activity.findViewById<View>(android.R.id.content), activity.resources.getString(R.string.reg_success), Snackbar.LENGTH_SHORT).show()
                        } else {
                            Snackbar.make(activity.findViewById<View>(android.R.id.content), activity.resources.getString(R.string.verification_problem) + " " +task.exception.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
        }
    }
}
