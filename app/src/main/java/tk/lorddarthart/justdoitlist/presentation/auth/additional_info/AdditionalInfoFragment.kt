package tk.lorddarthart.justdoitlist.presentation.auth.additional_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.databinding.FragmentAdditionalInfoBinding
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant

class AdditionalInfoFragment : BaseFragment(), AdditionalInfoFragmentView {
    @InjectPresenter lateinit var additionalInfoPresenter: AdditionalInfoPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentAdditionalInfoBinding) {
            buttonDisagree.setOnClickListener {
                activity.supportFragmentManager.popBackStack()
            }
            buttonAgree.setOnClickListener {
                with(AlertDialog.Builder(activity)) {
                    setTitle(getString(R.string.agreement_are_you_sure_text))
                    setMessage(getString(R.string.agreement_decline_text))
                    setPositiveButton(resources.getString(R.string.btn_exit)) { _, _ ->
                        closeApplication()
                    }
                    setNegativeButton(resources.getString(R.string.btn_stay)) { dialog, _ ->
                        dialog.cancel()
                    }
                    create()
                    show()
                }
            }
        }
    }

    override fun start() {
        activity.supportActionBar?.elevation = 0f
        with (fragmentBinding as FragmentAdditionalInfoBinding) {
            if (arguments != null && requireArguments().containsKey(ArgumentsKeysConstant.ACTIVITY)) {
                if (requireArguments().getString(ArgumentsKeysConstant.ACTIVITY)!! == DefaultValuesConstant.TERMS_AND_CONDITIONS) {
                    additionalInfoHeadTitle.text = getString(R.string.terms_and_conditions)
                    agreementText.text = getString(R.string.terms_condition_txt)
                }
                if (requireArguments().getString(ArgumentsKeysConstant.ACTIVITY)!! == (DefaultValuesConstant.PRIVACY_POLICY)) {
                    additionalInfoHeadTitle.text = getString(R.string.privacy_policy)
                    agreementText.text = getString(R.string.privacy_policy_txt)
                }
            }
        }
    }

    private fun closeApplication() {
        activity.finishAffinity()
    }
}
