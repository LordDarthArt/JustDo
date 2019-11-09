package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.sort

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.sort.SortFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSortBinding

class SortFragment : BaseFragment(), SortFragmentView {
    private lateinit var sortFragmentBinding: FragmentSortBinding

    @InjectPresenter
    lateinit var sortFragmentPresenter: SortFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sortFragmentBinding = FragmentSortBinding.inflate(inflater, container, false)

        // do something

        return sortFragmentBinding.root
    }
}
