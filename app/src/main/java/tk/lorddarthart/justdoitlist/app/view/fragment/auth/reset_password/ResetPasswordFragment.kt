package tk.lorddarthart.justdoitlist.app.view.fragment.auth.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.reset_password.ResetPasswordPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentResetPasswordBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator.isValidEmailAddress

class ResetPasswordFragment : BaseAuthFragment(), ResetPasswordFragmentView {
    private lateinit var resetPasswordFragmentBinding: FragmentResetPasswordBinding

    @InjectPresenter
    lateinit var resetPasswordPresenter: ResetPasswordPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        resetPasswordFragmentBinding = FragmentResetPasswordBinding.inflate(inflater, container, false)

        initialization()

        return resetPasswordFragmentBinding.root
    }

    override fun start() {
        if (activity.intent.hasExtra(IntentExtraConstNames.EMAIL)) {
            resetPasswordFragmentBinding.passwordResetEmailInput.setText(activity.intent.getStringExtra(IntentExtraConstNames.EMAIL))
        }
    }

    override fun initListeners() {
        resetPasswordFragmentBinding.resetPasswordSendRequestButton.setOnClickListener {
            if (isValidEmailAddress(resetPasswordFragmentBinding.passwordResetEmailInput.text.toString())) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(resetPasswordFragmentBinding.passwordResetEmailInput.text.toString(), resetPasswordPresenter.actionCodeSettings)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                activity.supportFragmentManager.popBackStack()
                                androidMainWindow.shortSnackbar { "Password reset instructions have been sent. Please check your email" }
                            } else {
                                logError(task.exception) { "Can't send password reset, the problem is: " }
                            }
                        }
            } else {
                androidMainWindow.shortSnackbar { "email is not valid" }
            }
        }
    }
}
