package tk.lorddarthart.justdoitlist.view.main.home.todo.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.main.todo.add.AddPresenter
import tk.lorddarthart.justdoitlist.databinding.AddFragmentBinding
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import javax.inject.Inject

class AddFragment : BaseMainFragment(), AddFragmentView {
    @Inject lateinit var priorityConverter: PriorityConverter
    @InjectPresenter lateinit var addPresenter: AddPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = AddFragmentBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        with (fragmentBinding as AddFragmentBinding) {
            addHeadTitle.text = getString(R.string.create_new_task)
            activity.setSupportActionBar(addHead)
        }
        activity.supportActionBar?.let { toolbar ->
            with (toolbar) {
                title = ""
                setDisplayHomeAsUpEnabled(true)
            }
        }
        return fragmentBinding.root
    }

    override fun initListeners() {
        with(fragmentBinding as AddFragmentBinding) {
            cardViewUrgentlyNewTodoLayout.setOnClickListener { addPresenter.onTick(PRIORITY_URGENTLY) }
            cardViewImportantNewTodoLayout.setOnClickListener { addPresenter.onTick(PRIORITY_IMPORTANT) }
            cardViewNormalNewToDoLayout.setOnClickListener { addPresenter.onTick(PRIORITY_NORMAL) }
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
        JustDoItListApp.component?.inject(this)

        priorityConverter.getPriorityCode(getString(R.string.priority_neutral))?.let { priority ->
            logDebug { "Priority was set to neutral" }
            addPresenter.priority = priority
        }
    }

    override fun replacePriority(priorityTag: String) {
        logDebug { "Priority was set to $priorityTag" }
        with(fragmentBinding as AddFragmentBinding) {
            priorityUrgentlySelectedIndicator.setVisibility(priorityTag == PRIORITY_URGENTLY)
            priorityImportantSelectedIndicator.setVisibility(priorityTag == PRIORITY_IMPORTANT)
            priorityNormalSelectedIndicator.setVisibility(priorityTag == PRIORITY_NORMAL)
        }
    }
}
