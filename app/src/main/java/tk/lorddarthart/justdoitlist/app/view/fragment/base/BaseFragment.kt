package tk.lorddarthart.justdoitlist.app.view.fragment.base

import android.content.Context
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.justdoitlist.util.moxy.MvpFragment

abstract class BaseFragment : MvpFragment(), IBaseFragment, Loggable {
    protected lateinit var activity: BaseActivity
    protected lateinit var fragmentBinding: ViewDataBinding
    protected lateinit var androidMainWindow: View
    protected val actionBar: ActionBar?
        get() = activity.supportActionBar

    protected val TAG = javaClass.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity
        androidMainWindow = activity.findViewById<View>(android.R.id.content)
    }

    override fun initialization() {
        initListeners()
        start()
    }
}