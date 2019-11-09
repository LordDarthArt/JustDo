package tk.lorddarthart.justdoitlist.util.converters

import android.content.ContextWrapper
import android.view.View
import androidx.core.content.ContextCompat
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App

object PriorityConverter {
    fun getPriority(priority: Long?): String? {
        return when (priority) {
            0L -> {
                App.instance.resources?.getString(R.string.priority_neutral)
            }
            1L -> {
                App.instance.resources?.getString(R.string.priority_normal)
            }
            2L -> {
                App.instance.resources?.getString(R.string.priority_important)
            }
            3L -> {
                App.instance.resources?.getString(R.string.priority_urgently)
            }

            else -> App.instance.resources?.getString(R.string.priority_neutral)
        }
    }

    fun getColor(priority: String?): Int? {
        with(App.instance) {
            return when (priority) {
                resources.getString(R.string.priority_neutral) -> {
                    ContextCompat.getColor(this, R.color.emptyPriorityMarkColor)
                }
                resources.getString(R.string.priority_normal) -> {
                    ContextCompat.getColor(this, R.color.lowPriorityMarkColor)
                }
                resources.getString(R.string.priority_important) -> {
                    ContextCompat.getColor(this, R.color.middlePriorityMarkColor)
                }
                resources.getString(R.string.priority_urgently) -> {
                    ContextCompat.getColor(this, R.color.highPriorityMarkColor)
                }
                else -> ContextCompat.getColor(this, R.color.emptyPriorityMarkColor)
            }
        }
    }

    fun setPriority(priority: String?): Long? {
        with (App.instance.resources) {
            return when (priority) {
                getString(R.string.priority_neutral) -> {
                    0L
                }
                getString(R.string.priority_normal) -> {
                    1L
                }
                getString(R.string.priority_important) -> {
                    2L
                }
                getString(R.string.priority_urgently) -> {
                    3L
                }
                else -> 0L
            }
        }
    }
}