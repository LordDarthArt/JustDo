package tk.lorddarthart.justdoitlist.view.auth.resetpassword

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.auth.resetpassword.ResetPasswordPresenter
import tk.lorddarthart.justdoitlist.databinding.ResetPasswordFragmentBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.helper.shortSnackbar
import tk.lorddarthart.justdoitlist.util.verificators.PasswordEmailValidator.isValidEmailAddress
import tk.lorddarthart.justdoitlist.view.auth.base.BaseAuthFragment
import javax.inject.Inject

class ResetPasswordFragment : BaseAuthFragment(), ResetPasswordFragmentView {
    @Inject lateinit var auth: FirebaseAuth
    @InjectPresenter lateinit var resetPasswordPresenter: ResetPasswordPresenter

    private val actionCodeSettings: ActionCodeSettings by lazy {
        ActionCodeSettings.newBuilder()
            .setUrl("https://tk-lorddarthart-justdo.firebaseapp.com")
            .setAndroidPackageName(requireContext().packageName, false, null)
            .build()
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = ResetPasswordFragmentBinding.inflate(inflater, container, false)
    }

    override fun start() {
        with (fragmentBinding as ResetPasswordFragmentBinding) {
            with (activity) {
                setSupportActionBar(resetPasswordHeader)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                resetPasswordHeaderTitle.text = getString(R.string.reset_password)
                if (intent.hasExtra(IntentExtraConstNames.EMAIL)) {
                    passwordResetEmailInput.setText(intent.getStringExtra(IntentExtraConstNames.EMAIL))
                }
            }
        }
    }

    override fun initListeners() {
        with(fragmentBinding as ResetPasswordFragmentBinding) {
            resetPasswordSendRequestButton.setOnClickListener {
                if (isValidEmailAddress(passwordResetEmailInput.text.toString())) {
                    auth.sendPasswordResetEmail(passwordResetEmailInput.text.toString(), actionCodeSettings)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                router.baseNavigator.popBackStack()
                                root.shortSnackbar { getString(R.string.password_reset_instructions_has_been_set) }
                            } else {
                                logError(task.exception) { "Can't send password reset, the problem is: ${task.exception?.message}" }
                            }
                        }
                } else {
                    root.shortSnackbar { getString(R.string.error_email_not_valid) }
                }
            }
        }
    }
}
