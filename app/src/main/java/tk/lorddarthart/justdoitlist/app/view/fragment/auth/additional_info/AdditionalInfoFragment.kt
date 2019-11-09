package tk.lorddarthart.justdoitlist.app.view.fragment.auth.additional_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_additional_info.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.auth.additional_info.AdditionalInfoFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAdditionalInfoBinding
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.util.constants.IntentExtraConstValues
import tk.lorddarthart.justdoitlist.util.listeners.BaseBackPressedListener

class AdditionalInfoFragment : BaseFragment(), AdditionalInfoFragmentView {
    private lateinit var additionalInfoFragmentBinding: FragmentAdditionalInfoBinding

    @InjectPresenter
    lateinit var additionalInfoFragmentPresenter: AdditionalInfoFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        additionalInfoFragmentBinding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)

        initialization()

        return additionalInfoFragmentBinding.root
    }

    fun initialization() {
        start()
        initListeners()
    }

    private fun start() {
        activity.supportActionBar?.elevation = 0f
    }

    private fun initListeners() {
        if (arguments != null && arguments!!.containsKey(IntentExtraConstNames.activity)) {
            if (arguments!!.getString(IntentExtraConstNames.activity)!! == IntentExtraConstValues.mTermsConditions) {
                additionalInfoFragmentBinding.agreementText.text = getString(R.string.terms_condition_txt)
            }
            if (arguments!!.getString(IntentExtraConstNames.activity)!! == (IntentExtraConstValues.mPrivacyPolicy)) {
                additionalInfoFragmentBinding.agreementText.text = getString(R.string.privacy_policy_txt)
            }
        }
        additionalInfoFragmentBinding.buttonDisagree.setOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }
        additionalInfoFragmentBinding.buttonAgree.setOnClickListener {
            with(AlertDialog.Builder(activity)) {
                setTitle(getString(R.string.agreement_areyousure))
                setMessage(getString(R.string.agreement_declinetext))
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

    private fun closeApplication() {
        activity.finishAffinity()
    }
}
