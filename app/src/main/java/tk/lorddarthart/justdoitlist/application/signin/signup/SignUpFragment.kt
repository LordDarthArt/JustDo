package tk.lorddarthart.justdoitlist.application.signin.signup

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.util.regex.Pattern
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.apache.commons.validator.routines.EmailValidator
import tk.lorddarthart.justdoitlist.utils.HidePass
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.signin.login.LogInFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mView: View

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
            val confirmpassword = view.tvSignUpConfirmPassword.text.toString()
            if (email!=""&&password!=""&&isValidEmailAddress(tvSignUpEmail.text.toString())&&isValidPassword(view.tvSignUpPassword.text.toString())&&password == confirmpassword) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                mAuth.signInWithEmailAndPassword(email, password)
                                sendVerificationEmail(view)
                            } else {
                                Snackbar.make(view, "Sign Up failed",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            } else {
                if (password!=confirmpassword) {
                    view.tilSignUpConfirmPassword.error = "passwords don't match"
                }
                if (!isValidEmailAddress(tvSignUpEmail.text.toString())) {
                    view.tilSignUpEmail.error = "email is not valid"
                }
                if (!isValidPassword(tvSignUpPassword.text.toString())) {
                    view.tilSignUpPassword.error = "password is not valid"
                }
                if (email=="") {
                    view.tilSignUpEmail.error = "email can't be empty"
                }
                if (password=="") {
                    view.tilSignUpPassword.error = "password can't be empty"
                }
                if (confirmpassword=="") {
                    view.tilSignUpConfirmPassword.error = "password confirmation can't be empty"
                }
            }
        }
    }

    private fun sendVerificationEmail(view: View) {
        val user = FirebaseAuth.getInstance().currentUser

        user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().signOut()
                        Snackbar.make(view, "Email verification has been sent. Please check your email",
                                Toast.LENGTH_SHORT).show()
                        fragmentManager!!.beginTransaction().replace(R.id.fragment_enter, LogInFragment()).commit()
                    } else {
                        Snackbar.make(view, "", Snackbar.LENGTH_LONG).show()
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
