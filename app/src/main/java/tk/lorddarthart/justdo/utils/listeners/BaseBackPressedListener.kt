package tk.lorddarthart.justdo.utils.listeners

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import tk.lorddarthart.justdo.R

class BaseBackPressedListener(private val activity: FragmentActivity) : OnBackPressedListener {

    override fun doBack() {
        activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun doBackFromAgreements() {
        with(AlertDialog.Builder(activity)) {
            setTitle("")
            setMessage("")
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