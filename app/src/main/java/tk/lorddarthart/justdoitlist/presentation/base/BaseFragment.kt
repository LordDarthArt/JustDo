package tk.lorddarthart.justdoitlist.presentation.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.justdoitlist.App
import tk.lorddarthart.justdoitlist.model.holder.ToDoListHolder
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.router.IRouter
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.smartnavigation.NavigatableFragment
import javax.inject.Inject

abstract class BaseFragment : NavigatableFragment(), IBaseFragment, Loggable {
    @Inject protected lateinit var router: IRouter
    @Inject protected lateinit var toDoListHolder: ToDoListHolder

    protected lateinit var activity: RootActivity
    protected lateinit var fragmentBinding: ViewDataBinding

    override val backStackKey: String by lazy {
        javaClass.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as RootActivity
    }

    override fun initialization() {
        App.component?.inject(this)

        with (router) {
            clearBackStack()
            baseNavigator.init(activity.supportFragmentManager)
        }

        activity.supportActionBar?.title = ""

        initListeners()
        start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this !is ITab) {  }
    }
}