package tk.lorddarthart.justdoitlist.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.model.holder.ToDoHolder
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.smartnavigation.NavigatableFragment
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseFragment : NavigatableFragment(), IBaseFragment, Loggable {
    @Inject protected lateinit var router: Router
    @Inject protected lateinit var toDoHolder: ToDoHolder

    protected var activity by Delegates.notNull<RootActivity>()
    protected var fragmentBinding by Delegates.notNull<ViewDataBinding>()

    override val backStackKey: String by lazy {
        javaClass.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as RootActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initialization(inflater, container)
        return fragmentBinding.root
    }

    override fun initialization(inflater: LayoutInflater, container: ViewGroup?) {
        JustDoItListApp.component?.inject(this)

        activity.supportActionBar?.title = ""

        initBinding(inflater, container)
        initListeners()
        start()
    }
}