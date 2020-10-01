package tk.lorddarthart.justdoitlist.presentation.auth.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.presentation.auth.base.BaseAuthFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentResetPasswordBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator.isValidEmailAddress

class ResetPasswordFragment : BaseAuthFragment(), ResetPasswordFragmentView {
    @InjectPresenter lateinit var resetPasswordPresenter: ResetPasswordPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentResetPasswordBinding.inflate(inflater, container, false)
    }

    override fun start() {
        if (activity.intent.hasExtra(IntentExtraConstNames.EMAIL)) {
            (fragmentBinding as FragmentResetPasswordBinding).passwordResetEmailInput.setText(activity.intent.getStringExtra(IntentExtraConstNames.EMAIL))
        }
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentResetPasswordBinding) {
            resetPasswordSendRequestButton.setOnClickListener {
                if (isValidEmailAddress(passwordResetEmailInput.text.toString())) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(passwordResetEmailInput.text.toString(), resetPasswordPresenter.actionCodeSettings)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    activity.supportFragmentManager.popBackStack()
                                    fragmentBinding.root.shortSnackbar { "Password reset instructions have been sent. Please check your email" }
                                } else {
                                    logError(task.exception) { "Can't send password reset, the problem is: " }
                                }
                            }
                } else {
                    fragmentBinding.root.shortSnackbar { "email is not valid" }
                }
            }
        }
    }
}
