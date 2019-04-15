package tk.lorddarthart.justdoitlist.application.signin.signup

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.util.regex.Pattern
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.apache.commons.validator.routines.EmailValidator
import tk.lorddarthart.justdoitlist.utils.HidePass
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.signin.view.SignInFragment

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
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if (arguments!=null && arguments!!.containsKey("email")) {
            view.tvSignUpEmail.setText(arguments!!.getString("email"))
        }
        view.btnSignUp.setOnClickListener {
            view.tilSignUpEmail.error = null
            view.tvSignUpPassword.error = null
            view.tilSignUpConfirmPassword.error = null
            val email = view.tvSignUpEmail.text.toString()
            val password = view.tvSignUpPassword.text.toString()
            val confirmPassword = view.tvSignUpConfirmPassword.text.toString()
            if (email!=""&&password!=""&&isValidEmailAddress(tvSignUpEmail.text.toString())&&isValidPassword(view.tvSignUpPassword.text.toString())&&password == confirmPassword) {
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
                if (!isValidEmailAddress(tvSignUpEmail.text.toString())) {
                    mView.tilSignUpEmail.error = "email is not valid"
                }
                if (!isValidPassword(tvSignUpPassword.text.toString())) {
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

    fun isValidEmailAddress(email: String): Boolean {
        return EmailValidator.getInstance().isValid(email)
    }

    fun isValidPassword(password: String): Boolean {
        var result = false
        var capitalletters = 0
        if (password.length<8) { return result }
        else if (password.contains(" ")||password.contains("\n")) { return result }
        else {
            val digit = Pattern.compile("[0-9]")
            val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
            for (i in 0 until password.length) {
                if (password[i]==password[i].toUpperCase()) {
                    capitalletters++
                }
            }
            val hasCapitalLetters = capitalletters>=2
            val hasDigit = digit.matcher(password)
            val hasSpecial = special.matcher(password)
            result = hasCapitalLetters && hasDigit.find() && hasSpecial.find()
        }
        return result
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
