package tk.lorddarthart.justdoitlist.app.view.fragment.base

import android.content.Context
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.util.moxy.MvpFragment

open class BaseFragment : MvpFragment() {
    protected lateinit var activity: BaseActivity

    protected val TAG = javaClass.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity
    }
}