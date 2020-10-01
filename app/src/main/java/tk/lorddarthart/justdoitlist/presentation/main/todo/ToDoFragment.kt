package tk.lorddarthart.justdoitlist.presentation.main.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.databinding.FragmentToDoBinding
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import tk.lorddarthart.justdoitlist.presentation.main.todo.adapter.ToDoViewAdapter
import tk.lorddarthart.justdoitlist.util.helper.setGone
import tk.lorddarthart.justdoitlist.util.helper.setVisible
import tk.lorddarthart.smartnavigation.NavigationTab

class ToDoFragment : BaseFragment(), NavigationTab, ToDoFragmentView {
    @InjectPresenter lateinit var toDoPresenter: ToDoPresenter

    override var INSTANCE: NavigationTab?
        get() { return ToDoFragment.INSTANCE }
        set(value) { ToDoFragment.INSTANCE = value }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentToDoBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentToDoBinding) {
            addToDoButton.setOnClickListener { router.openAddFragment() }
        }
    }

    override fun start() {
        if (toDoHolder.toDoList.isEmpty()) {
            showLoading()
        } else {
            initializeAdapter(toDoHolder.toDoList)
        }
    }

    private fun showError() {
        with(fragmentBinding as FragmentToDoBinding) {
            errorLayout.setVisible()
            noToDoLayout.setGone()
            toDoLayout.setGone()
            loadingLayout.setGone()
        }
    }

    private fun showNoToDo() {
        with(fragmentBinding as FragmentToDoBinding) {
            errorLayout.setGone()
            noToDoLayout.setVisible()
            toDoLayout.setGone()
            loadingLayout.setGone()
        }
    }

    private fun showToDo() {
        with(fragmentBinding as FragmentToDoBinding) {
            errorLayout.setGone()
            noToDoLayout.setGone()
            toDoLayout.setVisible()
            loadingLayout.setGone()
        }
    }

    private fun showLoading() {
        with(fragmentBinding as FragmentToDoBinding) {
            errorLayout.setGone()
            noToDoLayout.setGone()
            toDoLayout.setGone()
            loadingLayout.setVisible()
        }
    }

    private fun initializeAdapter(todoofday: List<ToDoItemDayModel>) {
        with(fragmentBinding as FragmentToDoBinding) {
            val recyclerViewAdapter = ToDoViewAdapter(activity, todoofday)
            recyclerViewAdapter.setReturnCount(todoofday.size)
            val layoutManager = LinearLayoutManager(activity)

            with(toDoList) {
                this.layoutManager = layoutManager
                adapter = recyclerViewAdapter
            }
        }
    }

    companion object : NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
