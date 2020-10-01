package tk.lorddarthart.justdoitlist.presentation.main.additional_view.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.lorddarthart.justdoitlist.presentation.main.base.BaseMainTabFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentErrorBinding

class ErrorFragment : BaseMainTabFragment(), ErrorFragmentView {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentErrorBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentErrorBinding) {
            buttonRefreshError.setOnClickListener { router.moveToLoading() }
        }
    }

    override fun start() {

    }

    companion object {
        var INSTANCE: ErrorFragment? = null
    }
}
