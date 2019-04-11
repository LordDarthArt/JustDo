package tk.lorddarthart.justdoitlist.application.signin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.signin.SignInActivity
import tk.lorddarthart.justdoitlist.application.signin.policy.AdditionalnfoActivity
import tk.lorddarthart.justdoitlist.application.signin.login.LogInFragment
import tk.lorddarthart.justdoitlist.application.signin.signup.SignUpFragment
import tk.lorddarthart.justdoitlist.utils.IntentExtraConstValues
import tk.lorddarthart.justdoitlist.utils.IntentExtraConstNames

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@Suppress("DEPRECATION")
class SignInFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private lateinit var mActivity: SignInActivity
    private lateinit var fragment: Fragment

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as SignInActivity
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
        mView = inflater.inflate(R.layout.fragment_enter, container, false)
        mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_enter, LogInFragment()).commit()
        val btnSignUp = mView.findViewById<Button>(R.id.button_sign_up)
        val btnLogIn = mView.findViewById<Button>(R.id.button_log_in)
        val txtTermsConditions = mView.findViewById<TextView>(R.id.textview_terms_conditions)
        val txtPrivacyPolicy = mView.findViewById<TextView>(R.id.textview_privacy_policy)
        if (activity!!.intent.hasExtra(IntentExtraConstNames.mShowExtraNotifications)) {
            when (activity!!.intent.getStringExtra(IntentExtraConstNames.mShowExtraNotifications)) {
               IntentExtraConstValues.mPasswordResetSuccessful -> {
                   Snackbar.make(activity!!.findViewById(android.R.id.content), "Password reset instructions have been sent. Please check your email", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        btnSignUp.setOnClickListener {
            fragment = SignUpFragment()
            val bundle = Bundle()
            if (childFragmentManager.fragments[0].view?.tvLogInEmail?.text.toString()!="") {
                bundle.putString(IntentExtraConstNames.mEmail, childFragmentManager.fragments[0].view?.tvLogInEmail?.text.toString())
            }
            fragment.arguments = bundle
            fragmentManager!!.beginTransaction().replace(R.id.fragment_enter, fragment).commit()
            btnLogIn.setTextColor(mView.resources.getColor(R.color.txtDisColor))
            btnSignUp.setTextColor(mView.resources.getColor(R.color.txtColor))
        }
        btnLogIn.setOnClickListener {
            fragment = LogInFragment()
            val bundle = Bundle()
            if (childFragmentManager.fragments[0].view?.tvSignUpEmail?.text.toString()!="") {
                bundle.putString(IntentExtraConstNames.mEmail, childFragmentManager.fragments[0].view?.tvSignUpEmail?.text.toString())
            }
            fragment.arguments = bundle
            fragmentManager!!.beginTransaction().replace(R.id.fragment_enter, fragment).commit()
            btnSignUp.setTextColor(mView.resources.getColor(R.color.txtDisColor))
            btnLogIn.setTextColor(mView.resources.getColor(R.color.txtColor))
        }
        txtTermsConditions.setOnClickListener {
            val intent = Intent(activity, AdditionalnfoActivity::class.java)
            intent.putExtra(IntentExtraConstNames.mActivity,IntentExtraConstValues.mTermsConditions)
            startActivity(intent)
        }
        txtPrivacyPolicy.setOnClickListener {
            val intent = Intent(activity, AdditionalnfoActivity::class.java)
            intent.putExtra(IntentExtraConstNames.mActivity,IntentExtraConstValues.mPrivacyPolicy)
            startActivity(intent)
        }
        return mView
    }

    companion object {

        private const val TAG = "SignInFragment"

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
