package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.app.model.holder.ToDoListHolder
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.adapter.ToDoViewAdapter
import tk.lorddarthart.justdoitlist.app.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.ToDoPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentToDoBinding
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject

class ToDoFragment : BaseFragment(), ToDoFragmentView {
    @InjectPresenter lateinit var toDoPresenter: ToDoPresenter

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentToDoBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentToDoBinding) {
            addToDoButton.setOnClickListener { navUtils.openAddFragment() }
        }
    }

    override fun start() {
        if (toDoListHolder.toDoList.isEmpty()) {
            navUtils.showLoading()
        } else {
            initializeAdapter(toDoListHolder.toDoList)
        }
    }

    private fun initializeAdapter(todoofday: List<ToDoItemDayModel>) {
        with (fragmentBinding as FragmentToDoBinding) {
            val recyclerViewAdapter = ToDoViewAdapter(activity, todoofday)
            recyclerViewAdapter.setReturnCount(todoofday.size)
            val layoutManager = LinearLayoutManager(activity)

            with (toDoList) {
                this.layoutManager = layoutManager
                adapter = recyclerViewAdapter
            }
        }
    }

    companion object {
        var INSTANCE: ToDoFragment? = null
    }
}
