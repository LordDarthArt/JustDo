package tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.base.BaseMainTabFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentErrorBinding
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import javax.inject.Inject

class ErrorFragment : BaseMainTabFragment(), ErrorFragmentView {
    @Inject lateinit var navUtils: NavUtils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentErrorBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentErrorBinding) {
            buttonRefreshError.setOnClickListener { navUtils.showLoading() }
        }
    }

    override fun start() {

    }
}
