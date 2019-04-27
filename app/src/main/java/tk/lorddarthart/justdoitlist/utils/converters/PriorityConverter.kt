package tk.lorddarthart.justdoitlist.utils.converters

import android.view.View
import tk.lorddarthart.justdoitlist.R

object PriorityConverter {
    fun getPriority(priority: Long?, view: View?): String? {
       return  when (priority) {
            0L -> {
                view?.context?.resources?.getString(R.string.priority_neutral)
            }
            1L -> {
                view?.context?.resources?.getString(R.string.priority_normal)
            }
            2L -> {
                view?.context?.resources?.getString(R.string.priority_important)
            }
            3L -> {
                view?.context?.resources?.getString(R.string.priority_urgently)
            }

            else -> view?.context?.resources?.getString(R.string.priority_neutral)
        }
    }

    fun getColor(priority: String?, view: View?): Int? {
        return when (priority) {
            view?.context?.resources?.getString(R.string.priority_neutral) -> {
                view?.context?.resources?.getColor(R.color.emptyPriorityMarkColor)
            }
            view?.context?.resources?.getString(R.string.priority_normal) -> {
                view?.context?.resources?.getColor(R.color.lowPriorityMarkColor)
            }
            view?.context?.resources?.getString(R.string.priority_important) -> {
                view?.context?.resources?.getColor(R.color.middlePriorityMarkColor)
            }
            view?.context?.resources?.getString(R.string.priority_urgently) -> {
                view?.context?.resources?.getColor(R.color.highPriorityMarkColor)
            }
            else -> view?.context?.resources?.getColor(R.color.emptyPriorityMarkColor)
        }
    }

    fun setPriority(priority: String?, view: View?): Long? {
        return when (priority) {
            view?.context?.resources?.getString(R.string.priority_neutral) -> {
                0L
            }
            view?.context?.resources?.getString(R.string.priority_normal) -> {
                1L
            }
            view?.context?.resources?.getString(R.string.priority_important) -> {
                2L
            }
            view?.context?.resources?.getString(R.string.priority_urgently) -> {
                3L
            }

            else -> 0L
        }
    }
}