package tk.lorddarthart.justdoitlist.presentation.main.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentToDoBinding
import tk.lorddarthart.justdoitlist.presentation.main.todo.adapter.ToDoViewAdapter

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
            addToDoButton.setOnClickListener { router.openAddFragment() }
        }
    }

    override fun start() {
        if (toDoListHolder.toDoList.isEmpty()) {
            router.moveToLoading()
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
