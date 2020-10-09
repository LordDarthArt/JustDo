package tk.lorddarthart.justdoitlist.util.converters

import java.text.SimpleDateFormat
import java.util.*

class DayTitleConverterImpl: DayTitleConverter {
    override fun convertToPreferred(dateFormatTemplate: SimpleDateFormat, date: Date): String {
        var dateString = dateFormatTemplate.format(date)
        dateString = dateString.substring(0, 1).toUpperCase() + dateString.substring(1).toLowerCase()
        if (dateString.contains(" 0")) {
            dateString = dateString.replace(" 0", " ")
        }
        return dateString
    }
}