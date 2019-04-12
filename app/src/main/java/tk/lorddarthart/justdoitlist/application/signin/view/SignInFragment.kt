package tk.lorddarthart.justdoitlist.application.signin.view

import android.content.Context
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
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.signin.additionalInfo.AdditionalInfoFragment
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
    private lateinit var mActivity: BaseActivity
    private lateinit var mFragment: Fragment

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
        mView = inflater.inflate(R.layout.fragment_sign_in, container, false)
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
            mFragment = SignUpFragment()
            val bundle = Bundle()
            if (mActivity.supportFragmentManager.fragments[1].view?.tvLogInEmail?.text.toString()!="") {
                bundle.putString(IntentExtraConstNames.mEmail, mActivity.supportFragmentManager.fragments[1].view?.tvLogInEmail?.text.toString())
            }
            mFragment.arguments = bundle
            mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_enter, mFragment).commit()
            btnLogIn.setTextColor(mView.resources.getColor(R.color.txtDisColor))
            btnSignUp.setTextColor(mView.resources.getColor(R.color.txtColor))
        }
        btnLogIn.setOnClickListener {
            mFragment = LogInFragment()
            val bundle = Bundle()
            if (mActivity.supportFragmentManager.fragments[1].view?.tvSignUpEmail?.text.toString()!="") {
                bundle.putString(IntentExtraConstNames.mEmail, mActivity.supportFragmentManager.fragments[1].view?.tvSignUpEmail?.text.toString())
            }
            mFragment.arguments = bundle
            mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_enter, mFragment).commit()
            btnSignUp.setTextColor(mView.resources.getColor(R.color.txtDisColor))
            btnLogIn.setTextColor(mView.resources.getColor(R.color.txtColor))
        }
        txtTermsConditions.setOnClickListener {
            mFragment = AdditionalInfoFragment()
            val bundle = Bundle()
            bundle.putString(IntentExtraConstNames.mActivity,IntentExtraConstValues.mTermsConditions)
            mFragment.arguments = bundle
            mActivity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, mFragment).addToBackStack(null).commit()
        }
        txtPrivacyPolicy.setOnClickListener {
            mFragment = AdditionalInfoFragment()
            val bundle = Bundle()
            bundle.putString(IntentExtraConstNames.mActivity,IntentExtraConstValues.mPrivacyPolicy)
            mFragment.arguments = bundle
            mActivity.supportFragmentManager.beginTransaction().add(R.id.fragment_main, mFragment).addToBackStack(null).commit()
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
