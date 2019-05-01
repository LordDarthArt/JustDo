package tk.lorddarthart.justdoitlist.application.main.todo.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_todo.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.application.main.todo.controller.ToDoViewAdapter
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemDayModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ToDoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity
    private lateinit var mButtonAdd: ConstraintLayout

    private val TAG = javaClass.name.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        container?.removeAllViews() - Maybe i don't need this???
        mView = inflater.inflate(R.layout.fragment_todo, container, false)
        mButtonAdd = mView.findViewById(R.id.btnAdd)
        initializeAdapter(mView, mActivity.mToDoDay)
        mButtonAdd.setOnClickListener {
            val fragment = AddFragment()
            mActivity.supportFragmentManager.beginTransaction().add(R.id.fragment_todo, fragment).addToBackStack(null).commit()
        }
        return mView
    }

    private fun initializeAdapter(view: View, todoofday: List<ToDoItemDayModel>) {
        val recyclerViewAdapter = ToDoViewAdapter(activity, todoofday)
        view.rvToDo.adapter = recyclerViewAdapter
        recyclerViewAdapter.setReturnCount(todoofday.size)
        val layoutManager = LinearLayoutManager(activity)
        view.rvToDo.layoutManager = layoutManager
    }

    companion object {

        @JvmStatic
        fun newInstance() = ToDoFragment()
    }
}
