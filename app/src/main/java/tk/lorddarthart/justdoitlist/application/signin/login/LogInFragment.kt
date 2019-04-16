package tk.lorddarthart.justdoitlist.application.signin.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import org.apache.commons.validator.routines.EmailValidator
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.main.MainFragment
import tk.lorddarthart.justdoitlist.application.signin.passwordreset.view.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.utils.HidePass
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.utils.verificators.PasswordEmailValidator

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LogInFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity
    private lateinit var mLoadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        container?.removeAllViews() - Maybe i don't need this???
        mView = inflater.inflate(R.layout.fragment_log_in, container, false)
        mAuth = FirebaseAuth.getInstance()
        if (arguments != null && arguments!!.containsKey("email")) {
            mView.tvLogInEmail.setText(arguments!!.getString("email"))
        }
        if (activity!!.intent.hasExtra("email")) {
            mView.tvLogInEmail.setText(activity!!.intent.getStringExtra("email"))
        }
        mView.tvLogInFrgt.setOnClickListener {
            val fragment = ResetPasswordFragment()
            val bundle = Bundle()
            if (mView.tvLogInEmail.text != null && mView.tvLogInEmail.text.toString() != "") {
                bundle.putString(IntentExtraConstNames.mEmail, mView.tvLogInEmail.text.toString())
            }
            fragment.arguments = bundle
            mActivity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragment).addToBackStack(null).commit()
        }
        if (activity!!.intent.hasExtra(IntentExtraConstNames.mEmail)) {
            mView.tvLogInEmail.setText(activity!!.intent.getStringExtra(IntentExtraConstNames.mEmail))
        }
        mView.btnLogIn.setOnClickListener {
            val email = mView.tvLogInEmail.text.toString()
            val password = mView.tvLogInPassword.text.toString()
            if (email != "" && password != "") {
                mView.tilLogInEmail.error = null
                mView.tilLogInPassword.error = null
                if (!PasswordEmailValidator.isValidEmailAddress(mView.tvLogInEmail.text.toString())) {
                    mView.tilLogInEmail.error = mActivity.resources.getString(R.string.error_email_not_valid)
                }
                buildLoading()
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                if (mAuth.currentUser!!.isEmailVerified) {
                                    val mFragment = MainFragment()
                                    mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, mFragment).commit()
//                                    mAuth.verifyPasswordResetCode("123") - What the hell is this?
                                    mLoadingDialog.cancel()
                                } else {
                                    Snackbar.make(mActivity.findViewById<View>(android.R.id.content), "User's email hasn't been verified. Please check your email",
                                            Toast.LENGTH_SHORT).show()
                                    mAuth.signOut()
                                    mLoadingDialog.cancel()
                                }
                            } else {
                                Log.d(TAG, "Sign-in failed, the reason is: ", task.exception)

                                Snackbar.make(mActivity.findViewById<View>(android.R.id.content), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                                mLoadingDialog.cancel()
                            }
                        }
            } else {
                if (email == "") {
                    mView.tilLogInEmail.error = "email must be not empty"
                }
                if (password == "") {
                    mView.tilLogInPassword.error = "password must be not empty"
                }
            }
        }
        mView.ivLogInHidePass.tag = R.drawable.ic_eye_unvisible
        mView.ivLogInHidePass.setOnClickListener {
            HidePass().clickHidePass(mView.ivLogInHidePass, mView.tvLogInPassword)
        }
        return mView
    }

    private fun buildLoading() {
        mLoadingDialog = ProgressDialog(mActivity, R.style.AppCompatAlertDialogStyle)
        mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mLoadingDialog.setMessage(mActivity.resources.getString(R.string.progress_auth))
        mLoadingDialog.setCancelable(false)
        mLoadingDialog.show()
    }

    companion object {

        private const val TAG = "LogInFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LogInFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
