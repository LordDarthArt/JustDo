package tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.sign_in.SignInPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSignInBinding

class SignInFragment : BaseAuthFragment(), SignInFragmentView {
    private lateinit var loadingDialog: ProgressDialog

    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
                    activity.baseActivityPresenter.auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    if (activity.baseActivityPresenter.auth.currentUser!!.isEmailVerified) {
                                        val mFragment = MainFragment()
                                        activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_base, mFragment).commit()
                                        loadingDialog.cancel()
                                    } else {
                                        Snackbar.make(activity.findViewById<View>(android.R.id.content), "User's email hasn't been verified. Please check your email",
                                                Snackbar.LENGTH_SHORT).show()
                                        activity.baseActivityPresenter.auth.signOut()
                                        loadingDialog.cancel()
                                    }
                                } else {
                                    Log.d(TAG, "Sign-in failed, the reason is: ", task.exception)

                                    Snackbar.make(activity.findViewById<View>(android.R.id.content), "Authentication failed.",
                                            Snackbar.LENGTH_SHORT).show()
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
                activity.supportFragmentManager.beginTransaction().add(R.id.fragment_base, fragment).addToBackStack(null).commit()
            }
            signInPasswordHideIcon.setOnClickListener {
                signInPasswordHideIcon.clickHidePass(signInPasswordInput)
            }
        }
    }

    override fun start() {
        with(fragmentBinding as FragmentSignInBinding) {
            if (arguments != null && arguments!!.containsKey("email")) {
                signInEmailInput.setText(arguments!!.getString("email"))
            }
            if (activity.intent.hasExtra("email")) {
                signInEmailInput.setText(activity.intent.getStringExtra("email"))
            }
            if (activity.intent.hasExtra(ArgumentsKeysConstant.EMAIL)) {
                signInEmailInput.setText(activity.intent.getStringExtra(ArgumentsKeysConstant.EMAIL))
            }
            signInPasswordHideIcon.tag = R.drawable.ic_eye_unvisible
        }
    }
}
