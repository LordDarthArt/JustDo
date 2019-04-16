package tk.lorddarthart.justdoitlist.application.signin.signup

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.signin.view.SignInFragment
import tk.lorddarthart.justdoitlist.utils.HidePass
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.utils.verificators.PasswordEmailValidator

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mActivity: BaseActivity
    private lateinit var mView: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false)
//        container?.removeAllViews() - Maybe i don't need this???
        mView.ivSignUpHidePassword.tag = R.drawable.ic_eye_unvisible
        mView.ivSignUpHidePassword.setOnClickListener {
            HidePass().clickHidePass(mView.ivSignUpHidePassword, mView.tvSignUpPassword)
        }
        mView.ivSignUpHideConfirmPassword.tag = R.drawable.ic_eye_unvisible
        mView.ivSignUpHideConfirmPassword.setOnClickListener {
            HidePass().clickHidePass(mView.ivSignUpHideConfirmPassword, mView.tvSignUpConfirmPassword)
        }
        mAuth = FirebaseAuth.getInstance()
        if (arguments!=null && arguments!!.containsKey(IntentExtraConstNames.mEmail)) {
            mView.tvSignUpEmail.setText(arguments!!.getString(IntentExtraConstNames.mEmail))
        }
        mView.btnSignUp.setOnClickListener {
            mView.tilSignUpEmail.error = null
            mView.tvSignUpPassword.error = null
            mView.tilSignUpConfirmPassword.error = null
            val email = mView.tvSignUpEmail.text.toString()
            val password = mView.tvSignUpPassword.text.toString()
            val confirmPassword = mView.tvSignUpConfirmPassword.text.toString()
            if (email!=""&&password!=""
                    &&PasswordEmailValidator.isValidEmailAddress(tvSignUpEmail.text.toString())
                    &&PasswordEmailValidator.isValidPassword(mView.tvSignUpPassword.text.toString())
                    &&password == confirmPassword) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                // mAuth.signInWithEmailAndPassword(email, password) - Not sure this was the right step
                                sendVerificationEmail()
                            } else {
                                Snackbar.make(mActivity.findViewById<View>(android.R.id.content), mActivity.resources.getString(R.string.reg_fail), Snackbar.LENGTH_SHORT).show()
                            }
                        }
            } else {
                if (password!=confirmPassword) {
                    mView.tilSignUpConfirmPassword.error = "passwords don't match"
                }
                if (!PasswordEmailValidator.isValidEmailAddress(tvSignUpEmail.text.toString())) {
                    mView.tilSignUpEmail.error = "email is not valid"
                }
                if (!PasswordEmailValidator.isValidPassword(tvSignUpPassword.text.toString())) {
                    mView.tilSignUpPassword.error = "password is not valid"
                }
                if (email=="") {
                    mView.tilSignUpEmail.error = "email can't be empty"
                }
                if (password=="") {
                    mView.tilSignUpPassword.error = "password can't be empty"
                }
                if (confirmPassword=="") {
                    mView.tilSignUpConfirmPassword.error = "password confirmation can't be empty"
                }
            }
        }
        return mView
    }

    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            it.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            FirebaseAuth.getInstance().signOut()
                            val fragment = SignInFragment()
                            mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit()
                            Snackbar.make(mActivity.findViewById<View>(android.R.id.content), mActivity.resources.getString(R.string.reg_success), Snackbar.LENGTH_SHORT).show()
                        } else {
                            Snackbar.make(mActivity.findViewById<View>(android.R.id.content), mActivity.resources.getString(R.string.verification_problem) + " " +task.exception.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    companion object {

        private const val TAG = "SignUpFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SignUpFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
