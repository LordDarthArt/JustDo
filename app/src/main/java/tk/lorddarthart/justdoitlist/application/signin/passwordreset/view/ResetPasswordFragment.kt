package tk.lorddarthart.justdoitlist.application.signin.passwordreset.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reset_password.view.*
import org.apache.commons.validator.routines.EmailValidator
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.utils.listeners.BaseBackPressedListener
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstNames

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ResetPasswordFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

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
//        Inflate the layout for this fragment
//        container?.removeAllViews() - Maybe i don't need this???
        mActivity.setOnBackPressedListener(BaseBackPressedListener(mActivity))
        mView = inflater.inflate(R.layout.fragment_reset_password, container, false)

        // TODO: do something

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mActivity.intent.hasExtra(IntentExtraConstNames.mEmail)) {
            view.tvEmailResetPassword.setText(mActivity.intent.getStringExtra(IntentExtraConstNames.mEmail))
        }
        val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl("https://tk-lorddarthart-justdo.firebaseapp.com")
                .setAndroidPackageName(mActivity.packageName, false, null)
                .build()
        view.btnSendRequest.setOnClickListener {
            if (isValidEmailAddress(view.tvEmailResetPassword.text.toString())) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(view.tvEmailResetPassword.text.toString(), actionCodeSettings)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                mActivity.supportFragmentManager.popBackStack()
                                Snackbar.make(mActivity.findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT).show()
                            } else {
                                Log.d(TAG, "Can't send password reset, the problem is: ", task.exception)
                            }
                        }
            } else {
                Snackbar.make(mActivity.findViewById(android.R.id.content), "email is not valid", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmailAddress(email: String): Boolean {
        return EmailValidator.getInstance().isValid(email)
    }

    companion object {

        private const val TAG = "ResetPasswordFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ResetPasswordFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
