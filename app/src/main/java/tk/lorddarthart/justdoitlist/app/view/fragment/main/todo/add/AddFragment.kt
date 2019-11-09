package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_add.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.add.AddFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAddBinding
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter

class AddFragment : BaseFragment(), AddFragmentView {
    private lateinit var addFragmentBinding: FragmentAddBinding

    @InjectPresenter
    lateinit var addFragmentPresenter: AddFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        addFragmentBinding = FragmentAddBinding.inflate(inflater, container, false)

        activity.setActionBarTitle(getString(R.string.todo_add_bar_title))

        PriorityConverter.setPriority(activity.resources.getString(R.string.priority_neutral))?.let {
            addFragmentPresenter.priority = it
            Log.d(TAG, "Priority was set to neutral")
        }

        with(addFragmentBinding) {
            cardViewUrgentlyNewTodoLayout.setOnClickListener {
                onTick(it as ConstraintLayout, priorityUrgentlyText)
                Log.d(TAG, "Priority was set to urgently")
            }
            cardViewImportantNewTodoLayout.setOnClickListener {
                onTick(it as ConstraintLayout, priorityImportantText)
                Log.d(TAG, "Priority was set to important")
            }
            cardViewNormalNewToDoLayout.setOnClickListener {
                onTick(it as ConstraintLayout, priorityNormalText)
                Log.d(TAG, "Priority was set to normal")
            }
            newTodoDescriptionInput.setOnTouchListener { view, event ->
                view.parent.requestDisallowInterceptTouchEvent(true)
                if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    view.parent.requestDisallowInterceptTouchEvent(false)
                }
                return@setOnTouchListener false
            }
        }

        return addFragmentBinding.root
    }

    override fun onDestroyView() {
        activity.setOnBackPressedListener(null)
        super.onDestroyView()
    }

    private fun onTick(constraintLayout: ConstraintLayout, textView: TextView) {
        if (PriorityConverter.setPriority(textView.text.toString()) != addFragmentPresenter.priority) {
            when (constraintLayout.id) {
                addFragmentBinding.cardViewUrgentlyNewTodoLayout.id -> {
                    with(addFragmentBinding) {
                        priorityUrgentlySelectedIndicator.visibility = View.VISIBLE
                        priorityImportantSelectedIndicator.visibility = View.GONE
                        priorityNormalSelectedIndicator.visibility = View.GONE
                    }
                }
                addFragmentBinding.cardViewImportantNewTodoLayout.id -> {
                    with(addFragmentBinding) {
                        priorityUrgentlySelectedIndicator.visibility = View.GONE
                        priorityImportantSelectedIndicator.visibility = View.VISIBLE
                        priorityNormalSelectedIndicator.visibility = View.GONE
                    }
                }
                addFragmentBinding.cardViewNormalNewToDoLayout.id -> {
                    with(addFragmentBinding) {
                        priorityUrgentlySelectedIndicator.visibility = View.GONE
                        priorityImportantSelectedIndicator.visibility = View.GONE
                        priorityNormalSelectedIndicator.visibility = View.VISIBLE
                    }
                }
            }
            PriorityConverter.setPriority(textView.text.toString())?.let {
                addFragmentPresenter.priority = it
            }
        } else {
            with(addFragmentBinding) {
                priorityUrgentlySelectedIndicator.visibility = View.GONE
                priorityImportantSelectedIndicator.visibility = View.GONE
                priorityNormalSelectedIndicator.visibility = View.GONE
            }
            PriorityConverter.setPriority(getString(R.string.priority_neutral))?.let {
                addFragmentPresenter.priority = it
            }
        }
    }
}
