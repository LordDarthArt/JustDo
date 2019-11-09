package tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.no_to_do

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.additional_view.no_to_do.NoToDoFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentNoToDoBinding

class NoToDoFragment : BaseFragment(), NoToDoFragmentView {
    private lateinit var noToDoFragmentBinding: FragmentNoToDoBinding

    @InjectPresenter
    lateinit var noToDoFragmentPresenter: NoToDoFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        noToDoFragmentBinding = FragmentNoToDoBinding.inflate(inflater, container, false)

        noToDoFragmentBinding.buttonNoTasksCreateOne.setOnClickListener {
            val fragment = AddFragment()
            activity.supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_todo, fragment).addToBackStack(null)
                    .commit()
        }

        return noToDoFragmentBinding.root
    }
}
