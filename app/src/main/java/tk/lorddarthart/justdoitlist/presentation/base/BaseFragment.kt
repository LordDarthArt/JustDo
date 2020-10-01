package tk.lorddarthart.justdoitlist.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.App
import tk.lorddarthart.justdoitlist.model.holder.ToDoListHolder
import tk.lorddarthart.justdoitlist.presentation.auth.AuthPresenter
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.router.IRouter
import tk.lorddarthart.justdoitlist.util.helper.Loggable
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initialization(inflater, container)
        return fragmentBinding.root
    }

    override fun initialization(inflater: LayoutInflater, container: ViewGroup?) {
        App.component?.inject(this)

        activity.supportActionBar?.title = ""

        initBinding(inflater, container)
        initListeners()
        start()
    }
}