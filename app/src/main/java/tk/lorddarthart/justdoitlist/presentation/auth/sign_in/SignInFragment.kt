package tk.lorddarthart.justdoitlist.presentation.auth.sign_in

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSignInBinding
import tk.lorddarthart.justdoitlist.presentation.auth.reset_password.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.presentation.main.MainFragment
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject

class SignInFragment : BaseAuthFragment(), SignInFragmentView {
    private lateinit var loadingDialog: ProgressDialog
    @Inject lateinit var auth: FirebaseAuth

    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentSignInBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
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
        with(fragmentBinding as FragmentSignInBinding) {
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
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    if (auth.currentUser?.isEmailVerified == true) {
                                        val mFragment = MainFragment()
                                        router.baseNavigator.navigate(mFragment, NavigationActionType.ReplaceAction, NavigationAnimType.NoAnim)
                                        loadingDialog.cancel()
                                    } else {
                                        fragmentBinding.root.shortSnackbar { "User's email hasn't been verified. Please check your email" }
                                        auth.signOut()
                                        loadingDialog.cancel()
                                    }
                                } else {
                                    logError(task.exception) { "Sign-in failed, the reason is: " }

                                    fragmentBinding.root.shortSnackbar { "Authentication failed." }
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
                val fragment = ResetPasswordFragment()
                val bundle = Bundle()
                if (!signInEmailInput.text.isNullOrBlank()) {
                    bundle.putString(ArgumentsKeysConstant.EMAIL, signInEmailInput.toString())
                }
                fragment.arguments = bundle
                router.baseNavigator.navigate(fragment, NavigationActionType.AddToBackStackAction, NavigationAnimType.SlideAnim)
            }
        }
    }

    override fun start() {
        with(fragmentBinding as FragmentSignInBinding) {
            if (arguments != null && (arguments?.containsKey("email") == true)) {
                signInEmailInput.setText(arguments?.getString("email"))
            }
            if (activity.intent.hasExtra("email")) {
                signInEmailInput.setText(activity.intent.getStringExtra("email"))
            }
            if (activity.intent.hasExtra(ArgumentsKeysConstant.EMAIL)) {
                signInEmailInput.setText(activity.intent.getStringExtra(ArgumentsKeysConstant.EMAIL))
            }
        }
    }
}
