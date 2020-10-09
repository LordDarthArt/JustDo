package tk.lorddarthart.justdoitlist.view.main.add

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.main.todo.add.AddPresenter
import tk.lorddarthart.justdoitlist.databinding.AddFragmentBinding
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_IMPORTANT
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_NORMAL
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_URGENTLY
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.helper.logDebug
import tk.lorddarthart.justdoitlist.util.helper.setVisibility
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class AddFragment : BaseMainFragment(), AddFragmentView {
    @Inject lateinit var priorityConverter: PriorityConverter
    @Inject lateinit var calendar: Calendar

    @Inject @field:[Named(DateFormatsTemplates.dayTime)] lateinit var dayTimeFormat: SimpleDateFormat
    @field:[Inject Named(DateFormatsTemplates.hours)] lateinit var hoursFormat: SimpleDateFormat
    @field:[Inject Named(DateFormatsTemplates.minutes)] lateinit var minutesFormat: SimpleDateFormat

    @InjectPresenter lateinit var addPresenter: AddPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = AddFragmentBinding.inflate(inflater, container, false)
        with (fragmentBinding as AddFragmentBinding) {
            addHeadTitle.text = getString(R.string.create_new_task)
            (requireActivity() as AppCompatActivity).setSupportActionBar(addHead)
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.let { toolbar ->
            with (toolbar) {
                title = ""
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun initListeners() {
        with(fragmentBinding as AddFragmentBinding) {
            cardViewUrgentlyNewTodoLayout.setOnClickListener { addPresenter.onTick(PRIORITY_URGENTLY) }
            cardViewImportantNewTodoLayout.setOnClickListener { addPresenter.onTick(PRIORITY_IMPORTANT) }
            cardViewNormalNewToDoLayout.setOnClickListener { addPresenter.onTick(PRIORITY_NORMAL) }
            newTodoTimeSelector.setOnClickListener { TimePickerDialog(requireContext(), { view, hourOfDay, minute -> newTodoTimeSelector.text = "${dayTimeFormat.apply { timeZone = TimeZone.getTimeZone("GMT") }.format((1000 * 60 * 60 * hourOfDay) + (1000 * 60 * minute))}" }, hoursFormat.apply { timeZone = calendar.timeZone }.format(dayTimeFormat.apply { timeZone = calendar.timeZone }.parse(newTodoTimeSelector.text.toString())).toString().toInt(), minutesFormat.apply { timeZone = calendar.timeZone }.format(dayTimeFormat.apply { timeZone = calendar.timeZone }.parse(newTodoTimeSelector.text.toString())).toString().toInt(), true).show()  }
            newTodoButtonCancel.setOnClickListener { router.baseNavigator.getBack() }
            newTodoButtonAdd.setOnClickListener {  }
        }
    }

    override fun start() {
        JustDoItListApp.component?.inject(this)

        with (fragmentBinding as AddFragmentBinding) {
            newTodoTimeSelector.text = addPresenter.getCurrentTime()
        }

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
