package tk.lorddarthart.justdoitlist.util.converters

import java.text.SimpleDateFormat
import java.util.*

interface DayTitleConverter {
    fun convertToPreferred(dateFormatTemplate: SimpleDateFormat, date: Date): String
}