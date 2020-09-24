package tk.lorddarthart.justdoitlist.app.view.fragment.base

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.justdoitlist.util.navigation.DaggerNavigationComponent
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.smartnavigation.NavigatableFragment
import javax.inject.Inject

abstract class BaseFragment : NavigatableFragment(), IBaseFragment, Loggable {
    @Inject protected lateinit var navUtils: NavUtils
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
        App.NavComponent?.inject(this)
        navUtils.init(activity.supportFragmentManager)

        activity.supportActionBar?.title = ""

        initListeners()
        start()
    }

    override fun onDestroy() {
        super.onDestroy()
        navUtils.removeFromBackStack(backStackKey)
    }
}