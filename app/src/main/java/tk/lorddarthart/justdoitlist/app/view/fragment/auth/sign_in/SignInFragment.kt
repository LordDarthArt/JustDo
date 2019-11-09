package tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.sign_in.SignInFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.reset_password.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.MainFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSignInBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.helper.clickHidePass
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator

class SignInFragment : BaseFragment(), SignInFragmentView {
    private lateinit var signInFragmentBinding: FragmentSignInBinding
    private lateinit var loadingDialog: ProgressDialog

    @InjectPresenter
    lateinit var signInFragmentPresenter: SignInFragmentPresenter

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        signInFragmentBinding = FragmentSignInBinding.inflate(inflater, container, false)
        if (arguments != null && arguments!!.containsKey("email")) {
            signInFragmentBinding.signInEmailInput.setText(arguments!!.getString("email"))
        }
        if (activity.intent.hasExtra("email")) {
            signInFragmentBinding.signInEmailInput.setText(activity.intent.getStringExtra("email"))
        }
        signInFragmentBinding.signInForgotPassButton.setOnClickListener {
            val fragment = ResetPasswordFragment()
            val bundle = Bundle()
            if (!signInFragmentBinding.signInEmailInput.text.isNullOrBlank()) {
                bundle.putString(IntentExtraConstNames.email, signInFragmentBinding.signInEmailInput.toString())
            }
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragment).addToBackStack(null).commit()
        }
        if (activity.intent.hasExtra(IntentExtraConstNames.email)) {
            signInFragmentBinding.signInEmailInput.setText(activity.intent.getStringExtra(IntentExtraConstNames.email))
        }
        signInFragmentBinding.signInButton.setOnClickListener {
            val email = signInFragmentBinding.signInEmailInput.text.toString()
            val password = signInFragmentBinding.signInPasswordInput.text.toString()
            if (email != "" && password != "") {
                signInFragmentBinding.signInEmailLayout.error = null
                signInFragmentBinding.signInPasswordLayout.error = null
                if (!PasswordEmailValidator.isValidEmailAddress(signInFragmentBinding.signInEmailInput.text.toString())) {
                    signInFragmentBinding.signInEmailLayout.error = getString(R.string.error_email_not_valid)
                }
                buildLoading()
                activity.baseActivityPresenter.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                if (activity.baseActivityPresenter.auth.currentUser!!.isEmailVerified) {
                                    val mFragment = MainFragment()
                                    activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, mFragment).commit()
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
                    email == "" -> signInFragmentBinding.signInEmailLayout.error = "email must be not empty"
                    password == "" -> signInFragmentBinding.signInPasswordLayout.error = "password must be not empty"
                }
            }
        }
        signInFragmentBinding.signInPasswordHideIcon.tag = R.drawable.ic_eye_unvisible
        signInFragmentBinding.signInPasswordHideIcon.setOnClickListener {
            signInFragmentBinding.signInPasswordHideIcon.clickHidePass(signInFragmentBinding.signInPasswordInput)
        }
        return signInFragmentBinding.root
    }

    private fun buildLoading() {
        loadingDialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        loadingDialog.setMessage(getString(R.string.progress_auth))
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }
}
