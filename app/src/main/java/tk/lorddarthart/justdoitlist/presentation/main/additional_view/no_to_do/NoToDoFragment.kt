package tk.lorddarthart.justdoitlist.presentation.main.additional_view.no_to_do

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentNoToDoBinding

class NoToDoFragment : BaseFragment(), NoToDoFragmentView {
    @InjectPresenter lateinit var noToDoPresenter: NoToDoPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentNoToDoBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {

    }

    override fun start() {
        with (fragmentBinding as FragmentNoToDoBinding) {
            buttonNoTasksCreateOne.setOnClickListener {
                router.openAddFragment()
            }
        }
    }

    companion object {
        var INSTANCE: NoToDoFragment? = null
    }
}
