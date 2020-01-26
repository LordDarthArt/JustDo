package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.sort.SortPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSortBinding

class SortFragment : BaseFragment(), SortFragmentView {
    @InjectPresenter
    lateinit var sortPresenter: SortPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentSortBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        // do something
    }

    override fun start() {
        // do something
    }
}
