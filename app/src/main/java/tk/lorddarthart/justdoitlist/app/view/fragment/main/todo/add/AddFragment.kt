package tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.add.AddPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAddBinding
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_IMPORTANT
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_NORMAL
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_URGENTLY
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter.getPriorityCode
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import tk.lorddarthart.justdoitlist.util.helper.setVisibility

class AddFragment : BaseMainFragment(), AddFragmentView {
    @InjectPresenter
    lateinit var addPresenter: AddPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentAddBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
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
        activity.setActionBarTitle(getString(R.string.todo_add_bar_title))

        getPriorityCode(activity.resources.getString(R.string.priority_neutral))?.let { priority ->
            addPresenter.priority = priority
            logDebug { "Priority was set to neutral" }
        }
    }

    override fun replacePriority(priorityTag: String) {
        with(fragmentBinding as FragmentAddBinding) {
            priorityUrgentlySelectedIndicator.setVisibility(priorityTag == PRIORITY_URGENTLY)
            priorityImportantSelectedIndicator.setVisibility(priorityTag == PRIORITY_URGENTLY)
            priorityNormalSelectedIndicator.setVisibility(priorityTag == PRIORITY_NORMAL)
        }
    }
}
