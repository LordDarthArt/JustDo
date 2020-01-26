package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.adapter.ToDoViewAdapter
import tk.lorddarthart.justdoitlist.app.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.ToDoPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentToDoBinding

class ToDoFragment : BaseFragment(), ToDoFragmentView {

    @InjectPresenter
    lateinit var toDoPresenter: ToDoPresenter

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentToDoBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentToDoBinding) {
            addToDoButton.setOnClickListener {
                toDoPresenter.openAddToDo()
            }
        }
    }

    override fun start() {
        initializeAdapter(activity.baseActivityPresenter.toDoList)
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
}
