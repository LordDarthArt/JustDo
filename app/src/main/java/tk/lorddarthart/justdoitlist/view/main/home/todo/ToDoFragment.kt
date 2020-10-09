package tk.lorddarthart.justdoitlist.view.main.home.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.main.todo.ToDoPresenter
import tk.lorddarthart.justdoitlist.databinding.ToDoFragmentBinding
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.helper.setGone
import tk.lorddarthart.justdoitlist.util.helper.setVisibility
import tk.lorddarthart.justdoitlist.util.helper.setVisible
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainTabFragment
import tk.lorddarthart.justdoitlist.view.main.home.todo.adapter.ToDoViewAdapter
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
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = ""
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
        with (toDoPresenter) {
            init()
            start()
        }
    }

    override fun render(toDoViewState: ToDoViewStates) {
        when (toDoViewState) {
            is ToDoViewStates.ToDoError -> { logError(toDoViewState.exception) { "Error during getting the TODO list" } }
            is ToDoViewStates.ToDoLoaded -> { initializeAdapter(toDoViewState.toDoList) }
            is ToDoViewStates.ToDoLoading -> { toDoPresenter.loadData() }
            is ToDoViewStates.ToDoEmpty -> { /* do nothing */ }
        }
        with (fragmentBinding as ToDoFragmentBinding) {
            errorLayout.setVisibility(toDoViewState is ToDoViewStates.ToDoError)
            noToDoLayout.setVisibility(toDoViewState is ToDoViewStates.ToDoEmpty)
            toDoLayout.setVisibility(toDoViewState is ToDoViewStates.ToDoLoaded)
            loadingLayout.setVisibility(toDoViewState is ToDoViewStates.ToDoLoading)
        }
    }

    private fun initializeAdapter(toDoList: List<ToDoItemDayModel>) {
        with ((fragmentBinding as ToDoFragmentBinding).toDoList) {
            adapter = ToDoViewAdapter(activity, toDoList).apply { setReturnCount(toDoList.size) }
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object : NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
