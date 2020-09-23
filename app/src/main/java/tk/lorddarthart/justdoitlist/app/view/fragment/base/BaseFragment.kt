package tk.lorddarthart.justdoitlist.app.view.fragment.base

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.smartnavigation.NavigatableFragment

abstract class BaseFragment : NavigatableFragment(), IBaseFragment, Loggable {
    protected lateinit var activity: BaseActivity
    protected lateinit var fragmentBinding: ViewDataBinding

    override val backStackKey: String by lazy {
        javaClass.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity
    }

    override fun initialization() {
        initListeners()
        start()
    }
}