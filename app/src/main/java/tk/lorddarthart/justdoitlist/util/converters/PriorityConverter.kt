package tk.lorddarthart.justdoitlist.util.converters

import androidx.core.content.ContextCompat
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.util.constants.DigitalConstant.PRIORITY_IMPORTANT_CODE
import tk.lorddarthart.justdoitlist.util.constants.DigitalConstant.PRIORITY_NEUTRAL_CODE
import tk.lorddarthart.justdoitlist.util.constants.DigitalConstant.PRIORITY_NORMAL_CODE
import tk.lorddarthart.justdoitlist.util.constants.DigitalConstant.PRIORITY_URGENTLY_CODE
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_IMPORTANT
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_NEUTRAL
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_NORMAL
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.PRIORITY_URGENTLY

object PriorityConverter {
    fun getPriorityName(priority: Long?): String? {
        with (App.INSTANCE) {
            return when (priority) {
                PRIORITY_NEUTRAL_CODE -> { getString(R.string.priority_neutral) }
                PRIORITY_NORMAL_CODE-> { getString(R.string.priority_normal) }
                PRIORITY_IMPORTANT_CODE -> { getString(R.string.priority_important) }
                PRIORITY_URGENTLY_CODE -> { getString(R.string.priority_urgently) }
                else -> getString(R.string.priority_neutral)
            }
        }
    }

    fun getColor(priority: Long?): Int {
        with(App.INSTANCE) {
            return when (priority) {
                0L -> { ContextCompat.getColor(this, R.color.emptyPriorityMarkColor) }
                1L -> { ContextCompat.getColor(this, R.color.lowPriorityMarkColor) }
                2L -> { ContextCompat.getColor(this, R.color.middlePriorityMarkColor) }
                3L -> { ContextCompat.getColor(this, R.color.highPriorityMarkColor) }
                else -> ContextCompat.getColor(this, R.color.emptyPriorityMarkColor)
            }
        }
    }

    fun getPriorityCode(priority: String?): Long? {
        return when (priority) {
            PRIORITY_NEUTRAL -> { PRIORITY_NEUTRAL_CODE }
            PRIORITY_NORMAL -> { PRIORITY_NORMAL_CODE }
            PRIORITY_IMPORTANT -> { PRIORITY_IMPORTANT_CODE }
            PRIORITY_URGENTLY -> { PRIORITY_URGENTLY_CODE }
            else -> 0L
        }
    }
}