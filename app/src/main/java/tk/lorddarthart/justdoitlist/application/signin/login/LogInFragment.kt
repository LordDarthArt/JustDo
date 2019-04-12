package tk.lorddarthart.justdoitlist.application.signin.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import tk.lorddarthart.justdoitlist.utils.HidePass
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.main.MainFragment
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.signin.passwordreset.view.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.utils.IntentExtraConstNames

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LogInFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

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
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments!=null && arguments!!.containsKey("email")) {
            view.tvLogInEmail.setText(arguments!!.getString("email"))
        }
        if (activity!!.intent.hasExtra("email")) {
            view.tvLogInEmail.setText(activity!!.intent.getStringExtra("email"))
        }
        view.tvLogInFrgt?.setOnClickListener {
            val fragment = ResetPasswordFragment()
            val bundle = Bundle()
            if (view.tvLogInEmail.text!=null && view.tvLogInEmail.text.toString() != "") {
                bundle.putString(IntentExtraConstNames.mEmail, view.tvLogInEmail.text.toString())
            }
            fragment.arguments = bundle
            mActivity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, fragment).addToBackStack(null).commit()
        }
        if (activity!!.intent.hasExtra(IntentExtraConstNames.mEmail)) {
            view.tvLogInEmail.setText(activity!!.intent.getStringExtra(IntentExtraConstNames.mEmail))
        }
        view.btnLogIn.setOnClickListener {
            val email = view.tvLogInEmail.text.toString()
            val password = view.tvLogInPassword.text.toString()
            if (email!=""&&password!="") {
                view.tilLogInEmail.error = null
                view.tilLogInPassword.error = null
                if (!isValidEmailAddress(view.tvLogInEmail.text.toString())) {
                    view.tilLogInEmail.error = "email is not valid"
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                if (mAuth.currentUser!!.isEmailVerified) {
                                    val mFragment = MainFragment()
                                    mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, mFragment).commit()
//                                    mAuth.verifyPasswordResetCode("123") - What the hell is this?
                                } else {
                                    Snackbar.make(view, "User's email hasn't been verified. Please check your email",
                                            Toast.LENGTH_SHORT).show()
                                    mAuth.signOut()
                                }
                            } else {
                                Log.d(TAG, "Sign-in failed, the reason is: ", task.exception)

                                Snackbar.make(view, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            } else {
                if (email=="") {
                    view.tilLogInEmail.error = "email must be not empty"
                }
                if (password=="") {
                    view.tilLogInPassword.error = "password must be not empty"
                }
            }
        }
        view.ivLogInHidePass.tag = R.drawable.ic_eye_unvisible
        view.ivLogInHidePass.setOnClickListener {
            HidePass().clickHidePass(view.ivLogInHidePass, view.tvLogInPassword)
        }
    }

    private fun isValidEmailAddress(email: String): Boolean {
        return EmailValidator.getInstance().isValid(email)
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
