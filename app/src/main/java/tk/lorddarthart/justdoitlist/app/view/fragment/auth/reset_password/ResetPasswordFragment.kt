package tk.lorddarthart.justdoitlist.app.view.fragment.auth.reset_password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.reset_password.ResetPasswordFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentResetPasswordBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator.isValidEmailAddress

class ResetPasswordFragment : BaseFragment(), ResetPasswordFragmentView {
    private lateinit var resetPasswordFragmentBinding: FragmentResetPasswordBinding

    @InjectPresenter
    lateinit var resetPasswordFragmentPresenter: ResetPasswordFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        resetPasswordFragmentBinding = FragmentResetPasswordBinding.inflate(inflater, container, false)

        initialization()

        return resetPasswordFragmentBinding.root
    }

    private fun initialization() {
        start()
        initListeners()
    }

    private fun start() {
        if (activity.intent.hasExtra(IntentExtraConstNames.email)) {
            resetPasswordFragmentBinding.passwordResetEmailInput.setText(activity.intent.getStringExtra(IntentExtraConstNames.email))
        }
    }

    private fun initListeners() {
        resetPasswordFragmentBinding.resetPasswordSendRequestButton.setOnClickListener {
            if (isValidEmailAddress(resetPasswordFragmentBinding.passwordResetEmailInput.text.toString())) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(resetPasswordFragmentBinding.passwordResetEmailInput.text.toString(), resetPasswordFragmentPresenter.actionCodeSettings)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                activity.supportFragmentManager.popBackStack()
                                Snackbar.make(activity.findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT).show()
                            } else {
                                Log.d(TAG, "Can't send password reset, the problem is: ", task.exception)
                            }
                        }
            } else {
                Snackbar.make(activity.findViewById(android.R.id.content), "email is not valid", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
