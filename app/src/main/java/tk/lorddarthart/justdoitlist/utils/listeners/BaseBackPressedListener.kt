package tk.lorddarthart.justdoitlist.utils.listeners

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity

class BaseBackPressedListener(private val activity: FragmentActivity) : OnBackPressedListener {

    override fun doBack() {
        (activity as BaseActivity).supportActionBar?.let { actionBar ->
            activity.getMainTitle()?.let { title ->
                actionBar.title = title
            }
        }
        activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun doBackFromAgreements() {
        with(AlertDialog.Builder(activity)) {
            setTitle(activity.resources.getString(R.string.agreement_areyousure))
            setMessage(activity.resources.getString(R.string.agreement_declinetext))
            setPositiveButton(activity.resources.getString(R.string.btn_exit)) { _, _ ->
                activity.finishAffinity()
            }
            setNegativeButton(activity.resources.getString(R.string.btn_stay)) { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }
    }
}