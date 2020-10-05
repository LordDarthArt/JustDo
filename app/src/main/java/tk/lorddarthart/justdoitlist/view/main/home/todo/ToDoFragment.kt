package tk.lorddarthart.justdoitlist.view.main.home.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.main.todo.ToDoPresenter
import tk.lorddarthart.justdoitlist.databinding.ToDoFragmentBinding
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainTabFragment
import tk.lorddarthart.smartnavigation.NavigationTab
import java.lang.Exception

class ToDoFragment : BaseMainTabFragment(), NavigationTab, ToDoFragmentView {
    @InjectPresenter lateinit var toDoPresenter: ToDoPresenter

    override var INSTANCE: NavigationTab?
        get() { return ToDoFragment.INSTANCE }
        set(value) { ToDoFragment.INSTANCE = value }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setupNavBar((fragmentBinding as ToDoFragmentBinding).toDoHead)
        return fragmentBinding.root
    }

    override fun setupNavBar(toolbar: Toolbar) {
        (fragmentBinding as ToDoFragmentBinding).toDoHeadTitle.text = getString(R.string.list)
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.title = ""
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = ToDoFragmentBinding.inflate(inflater, container, false)
    }

    override fun initialization() {
        super.initialization()
        setupNavBar((fragmentBinding as ToDoFragmentBinding).toDoHead)
    }

    override fun initListeners() {
        with(fragmentBinding as ToDoFragmentBinding) {
            addToDoButton.setOnClickListener { router.showAddFragment() }
            buttonNoTasksCreateOne.setOnClickListener { router.showAddFragment() }
            buttonRefreshError.setOnClickListener { toDoHolder.toDoList.clear(); start() }
        }
    }

    override fun start() {
        toDoPresenter.init()
        if (toDoHolder.toDoList.isEmpty()) {
            showLoading()
        } else {
            initializeAdapter(toDoHolder.toDoList)
        }
    }

    private fun showError() {
        with(fragmentBinding as ToDoFragmentBinding) {
            errorLayout.setVisible()
            noToDoLayout.setGone()
            toDoLayout.setGone()
            loadingLayout.setGone()
        }
    }

    private fun showNoToDo() {
        with(fragmentBinding as ToDoFragmentBinding) {
            errorLayout.setGone()
            noToDoLayout.setVisible()
            toDoLayout.setGone()
            loadingLayout.setGone()
        }
    }

    override fun showToDo() {
        with(fragmentBinding as ToDoFragmentBinding) {
            errorLayout.setGone()
            noToDoLayout.setGone()
            toDoLayout.setVisible()
            loadingLayout.setGone()
        }
    }

    private fun showLoading() {
        with(fragmentBinding as ToDoFragmentBinding) {
            errorLayout.setGone()
            noToDoLayout.setGone()
            toDoLayout.setGone()
            loadingLayout.setVisible()
        }
        toDoPresenter.loadData()
    }

    private fun initializeAdapter(todoofday: List<ToDoItemDayModel>) {
        with(fragmentBinding as ToDoFragmentBinding) {
            val recyclerViewAdapter = ToDoViewAdapter(activity, todoofday)
            recyclerViewAdapter.setReturnCount(todoofday.size)
            val layoutManager = LinearLayoutManager(activity)

            with(toDoList) {
                this.layoutManager = layoutManager
                adapter = recyclerViewAdapter
            }
        }
    }

    override fun noTasks() {
        showNoToDo()
    }

    override fun onFailure(e: Exception) {
        showError()
    }

    companion object : NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
