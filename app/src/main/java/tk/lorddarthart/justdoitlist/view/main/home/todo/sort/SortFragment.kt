package tk.lorddarthart.justdoitlist.view.main.home.todo.sort

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.bussiness.main.todo.sort.SortPresenter
import tk.lorddarthart.justdoitlist.databinding.SortFragmentBinding
import tk.lorddarthart.justdoitlist.view.base.BaseFragment

class SortFragment : BaseFragment(), SortFragmentView {
    @InjectPresenter lateinit var sortPresenter: SortPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = SortFragmentBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        // do something
    }

    override fun start() {
        // do something
    }
}
