package tk.lorddarthart.justdoitlist.presentation.main.todo.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSortBinding

class SortFragment : BaseFragment(), SortFragmentView {
    @InjectPresenter lateinit var sortPresenter: SortPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentSortBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        // do something
    }

    override fun start() {
        // do something
    }
}
