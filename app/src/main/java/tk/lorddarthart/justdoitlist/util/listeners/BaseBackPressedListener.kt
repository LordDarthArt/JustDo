package tk.lorddarthart.justdoitlist.util.listeners

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AlertDialog
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity

class BaseBackPressedListener(private val activity: FragmentActivity) : OnBackPressedListener {

    override fun doBack() {
        (activity as RootActivity).supportActionBar?.let { actionBar ->
            activity.getMainTitle()?.let { title ->
                actionBar.title = title
            }
        }
        activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun doBackFromAgreements() {
        with(AlertDialog.Builder(activity)) {
            setTitle(activity.resources.getString(R.string.agreement_are_you_sure_text))
            setMessage(activity.resources.getString(R.string.agreement_decline_text))
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