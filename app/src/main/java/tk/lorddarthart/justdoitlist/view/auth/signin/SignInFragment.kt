package tk.lorddarthart.justdoitlist.view.auth.signin

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.auth.signin.SignInPresenter
import tk.lorddarthart.justdoitlist.databinding.SignInFragmentBinding
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator
import tk.lorddarthart.justdoitlist.view.auth.base.BaseAuthFragment
import tk.lorddarthart.smartnavigation.NavigationTab
import javax.inject.Inject

class SignInFragment : BaseAuthFragment(), NavigationTab, SignInFragmentView {
    @Inject lateinit var auth: FirebaseAuth
    @InjectPresenter lateinit var signInPresenter: SignInPresenter

    private lateinit var loadingDialog: ProgressDialog

    override var INSTANCE: NavigationTab?
        get() { return SignInFragment.INSTANCE }
        set(value) { SignInFragment.INSTANCE = value }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = SignInFragmentBinding.inflate(inflater, container, false)
    }

    private fun buildLoading() {
        loadingDialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle).apply {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setMessage(getString(R.string.progress_auth))
            setCancelable(false)
            show()
        }
    }

    override fun initListeners() {
        with(fragmentBinding as SignInFragmentBinding) {
            signInButton.setOnClickListener {
                val email = signInEmailInput.text.toString()
                val password = signInPasswordInput.text.toString()
                if (email != "" && password != "") {
                    signInEmailLayout.error = null
                    signInPasswordLayout.error = null
                    if (!PasswordEmailValidator.isValidEmailAddress(signInEmailInput.text.toString())) {
                        signInEmailLayout.error = getString(R.string.error_email_not_valid)
                    }
                    buildLoading()
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                if (auth.currentUser?.isEmailVerified == true) {
                                    router.openNextAfterSplash()
                                    loadingDialog.cancel()
                                } else {
                                    root.shortSnackbar { "User's email hasn't been verified. Please check your email" }
                                    auth.signOut()
                                    loadingDialog.cancel()
                                }
                            } else {
                                logError(task.exception) { "Sign-in failed, the reason is: ${task.exception?.message}" }

                                root.shortSnackbar { "Authentication failed." }
                                loadingDialog.cancel()
                            }
                        }
                } else {
                    when {
                        email == "" -> signInEmailLayout.error = "email must be not empty"
                        password == "" -> signInPasswordLayout.error = "password must be not empty"
                    }
                }
            }
            signInForgotPassButton.setOnClickListener {
                val bundle = Bundle().apply {
                    if (!signInEmailInput.text.isNullOrBlank()) {
                        putString(ArgumentsKeysConstant.EMAIL, signInEmailInput.toString())
                    }
                }
                router.showResetPassword(bundle)
            }
        }
    }

    override fun start() {
        JustDoItListApp.component?.inject(this)
        with(fragmentBinding as SignInFragmentBinding) {
            if (arguments != null && (arguments?.containsKey(ArgumentsKeysConstant.EMAIL) == true)) {
                signInEmailInput.setText(arguments?.getString(ArgumentsKeysConstant.EMAIL))
            }
            if (requireActivity().intent.hasExtra(ArgumentsKeysConstant.EMAIL)) {
                signInEmailInput.setText(requireActivity().intent.getStringExtra(ArgumentsKeysConstant.EMAIL))
            }
        }
    }

    companion object : NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
