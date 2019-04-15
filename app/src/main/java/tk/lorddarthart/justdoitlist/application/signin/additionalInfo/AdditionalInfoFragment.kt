package tk.lorddarthart.justdoitlist.application.signin.additionalInfo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_additional_info.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.utils.listeners.BaseBackPressedListener
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstValues

class AdditionalInfoFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity.setOnBackPressedListener(BaseBackPressedListener(mActivity))
        mView = inflater.inflate(R.layout.fragment_additional_info, container, false)
        if (arguments != null && arguments!!.containsKey(IntentExtraConstNames.mActivity)) {
            if (arguments!!.getString(IntentExtraConstNames.mActivity)!! == IntentExtraConstValues.mTermsConditions) {
                mView.textView3.text = mView.resources.getString(R.string.terms_condition_txt)
            }
            if (arguments!!.getString(IntentExtraConstNames.mActivity)!! == (IntentExtraConstValues.mPrivacyPolicy)) {
                mView.textView3.text = mView.resources.getString(R.string.privacy_policy_txt)
            }
        }
        mView.btnAIOK.setOnClickListener {
            mActivity.supportFragmentManager.popBackStack()
            mActivity.setOnBackPressedListener(null)
        }
        mView.btnAINotOK.setOnClickListener {
            with(AlertDialog.Builder(mActivity)) {
                setTitle(mActivity.resources.getString(R.string.agreement_areyousure))
                setMessage(mActivity.resources.getString(R.string.agreement_declinetext))
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
        mActivity.supportActionBar?.elevation = 0f
        return mView
    }

    private fun closeApplication() {
        mActivity.finishAffinity()
    }
}
