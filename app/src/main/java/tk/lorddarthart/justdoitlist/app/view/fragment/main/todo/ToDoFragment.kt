package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.adapter.ToDoViewAdapter
import tk.lorddarthart.justdoitlist.app.model.model.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentToDoBinding

class ToDoFragment : BaseFragment() {
    private lateinit var toDoFragmentBinding: FragmentToDoBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        toDoFragmentBinding = FragmentToDoBinding.inflate(inflater, container, false)
        initializeAdapter(activity.baseActivityPresenter.toDoList)
        toDoFragmentBinding.addToDoButton.setOnClickListener {
            val fragment = AddFragment()
            activity.supportFragmentManager.beginTransaction().add(R.id.fragment_todo, fragment).addToBackStack(null).commit()
        }
        return toDoFragmentBinding.root
    }

    private fun initializeAdapter(todoofday: List<ToDoItemDayModel>) {
        val recyclerViewAdapter = ToDoViewAdapter(activity, todoofday)
        recyclerViewAdapter.setReturnCount(todoofday.size)
        val layoutManager = LinearLayoutManager(activity)

        toDoFragmentBinding.toDoList.layoutManager = layoutManager
        toDoFragmentBinding.toDoList.adapter = recyclerViewAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = ToDoFragment()
    }
}
