package tk.lorddarthart.justdoitlist.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.model.holder.ToDoHolder
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.justdoitlist.util.helper.Loggable
import tk.lorddarthart.smartnavigation.NavigatableFragment
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseFragment : NavigatableFragment(), IBaseFragment, Loggable {
    @Inject protected lateinit var router: Router
    @Inject protected lateinit var toDoHolder: ToDoHolder

    protected var fragmentBinding by Delegates.notNull<ViewDataBinding>()

    override val backStackKey: String by lazy { javaClass.simpleName }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        JustDoItListApp.component?.inject(this)
        initBinding(inflater, container)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun initialization() {
        activity?.actionBar?.title = ""

        initListeners()
        start()
    }
}