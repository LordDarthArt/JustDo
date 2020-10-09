package tk.lorddarthart.justdoitlist.view.auth.additionalinfo

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.auth.additionalinfo.AdditionalInfoPresenter
import tk.lorddarthart.justdoitlist.databinding.AdditionalInfoFragmentBinding
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant
import tk.lorddarthart.justdoitlist.view.base.BaseFragment

class AdditionalInfoFragment : BaseFragment(), AdditionalInfoFragmentView {
    @InjectPresenter lateinit var additionalInfoPresenter: AdditionalInfoPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = AdditionalInfoFragmentBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with(fragmentBinding as AdditionalInfoFragmentBinding) {
            buttonDisagree.setOnClickListener {
                (requireActivity() as AppCompatActivity).supportFragmentManager.popBackStack()
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
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = 0f
        with (fragmentBinding as AdditionalInfoFragmentBinding) {
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
        requireActivity().finishAffinity()
    }
}
