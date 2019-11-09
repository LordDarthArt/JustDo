package tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentErrorBinding

class ErrorFragment : BaseFragment() {
    private lateinit var errorFragmentBinding: FragmentErrorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        errorFragmentBinding = FragmentErrorBinding.inflate(inflater, container, false)

        initialization()

        return errorFragmentBinding.root
    }

    private fun initialization() {
        start()
        initListeners()
    }

    private fun initListeners() {
        errorFragmentBinding.buttonRefreshError.setOnClickListener {
            val fragment = LoadingFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_todo, fragment).commit()
        }
    }

    private fun start() {

    }
}
