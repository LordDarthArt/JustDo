package tk.lorddarthart.justdoitlist.util.converters

import android.content.Context

interface PriorityConverter {
    fun init(context: Context)
    fun getPriorityName(priority: Long?): String?
    fun getColor(priority: Long?): Int
    fun getPriorityCode(priority: String?): Long?
}