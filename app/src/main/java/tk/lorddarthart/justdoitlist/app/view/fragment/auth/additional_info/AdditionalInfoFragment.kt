package tk.lorddarthart.justdoitlist.app.view.fragment.auth.additional_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.additional_info.AdditionalInfoPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAdditionalInfoBinding
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.justdoitlist.util.constants.DefaultValuesConstant

class AdditionalInfoFragment : BaseFragment(), AdditionalInfoFragmentView {
    @InjectPresenter
    lateinit var additionalInfoPresenter: AdditionalInfoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun start() {
        activity.supportActionBar?.elevation = 0f
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentAdditionalInfoBinding) {
            if (arguments != null && arguments!!.containsKey(ArgumentsKeysConstant.ACTIVITY)) {
                if (arguments!!.getString(ArgumentsKeysConstant.ACTIVITY)!! == DefaultValuesConstant.TERMS_AND_CONDITIONS) {
                    agreementText.text = getString(R.string.terms_condition_txt)
                }
                if (arguments!!.getString(ArgumentsKeysConstant.ACTIVITY)!! == (DefaultValuesConstant.PRIVACY_POLICY)) {
                    agreementText.text = getString(R.string.privacy_policy_txt)
                }
            }
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

    private fun closeApplication() {
        activity.finishAffinity()
    }
}
