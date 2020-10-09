package tk.lorddarthart.justdoitlist.util.constants

import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import java.util.*

object DateFormatsTemplates {
    const val minutes = "mm"
    const val hours = "HH"
    const val fromDatabaseToTimestamp = "dd.MM.yyyy"
    const val dayTime = "HH:mm"
    const val year = "yyyy"
    const val month = "MM"
    const val monthWord = "MMMM"
    const val day = "dd"
    private lateinit var fromTimestampToTitle: String

    fun getFromTimestampToTitle(currentCalDate: Calendar, localeHelper: LocaleHelper): String{
        fromTimestampToTitle = if (!localeHelper.isRussianLocalization()) {
            val dayNumberSuffix = getDayNumberSuffix(currentCalDate.get(Calendar.DAY_OF_MONTH))
            "MMMM dd'$dayNumberSuffix', EEEE"
        } else {
            "EEEE, dd MMMM"
        }
        return fromTimestampToTitle
    }

    private fun getDayNumberSuffix(day: Int): String {
        return if (day in 11..13) {
            "th"
        } else {
            when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }
}