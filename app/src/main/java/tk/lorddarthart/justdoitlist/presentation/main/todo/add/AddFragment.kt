package tk.lorddarthart.justdoitlist.presentation.main.todo.add

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.databinding.FragmentAddBinding
import tk.lorddarthart.justdoitlist.databinding.FragmentToDoBinding
import tk.lorddarthart.justdoitlist.presentation.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_IMPORTANT
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_NORMAL
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_URGENTLY
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import tk.lorddarthart.justdoitlist.util.helper.setVisibility
import javax.inject.Inject

class AddFragment : BaseMainFragment(), AddFragmentView {
    @Inject lateinit var priorityConverter: PriorityConverter
    @InjectPresenter lateinit var addPresenter: AddPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentToDoBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with(fragmentBinding as FragmentAddBinding) {
            cardViewUrgentlyNewTodoLayout.setOnClickListener {
                addPresenter.onTick(PRIORITY_URGENTLY)
            }
            cardViewImportantNewTodoLayout.setOnClickListener {
                addPresenter.onTick(PRIORITY_IMPORTANT)
            }
            cardViewNormalNewToDoLayout.setOnClickListener {
                addPresenter.onTick(PRIORITY_NORMAL)
            }
//            TODO: MAKE SURE OF WHAT THAT IS
//            newTodoDescriptionInput.setOnTouchListener { view, event ->
//                view.parent.requestDisallowInterceptTouchEvent(true)
//                if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
//                    view.parent.requestDisallowInterceptTouchEvent(false)
//                }
//                return@setOnTouchListener false
//            }
        }
    }

    override fun start() {
        with (activity) {
            setActionBarTitle(getString(R.string.todo_add_bar_title))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        priorityConverter.getPriorityCode(getString(R.string.priority_neutral))?.let { priority ->
            addPresenter.priority = priority
            logDebug { "Priority was set to neutral" }
        }
    }

    override fun replacePriority(priorityTag: String) {
        with(fragmentBinding as FragmentAddBinding) {
            priorityUrgentlySelectedIndicator.setVisibility(priorityTag == PRIORITY_URGENTLY)
            priorityImportantSelectedIndicator.setVisibility(priorityTag == PRIORITY_IMPORTANT)
            priorityNormalSelectedIndicator.setVisibility(priorityTag == PRIORITY_NORMAL)
        }
    }
}
