package tk.lorddarthart.justdoitlist.utils.constants

import android.content.Context
import android.os.Build
import tk.lorddarthart.justdoitlist.utils.Locale.getLocale
import tk.lorddarthart.justdoitlist.utils.Locale.isRussianLocalization
import java.util.*

object DateFormatsTemplates {
    const val mFromDatabaseToTimestamp = "dd.MM.yyyy"
    const val mDayTime = "HH:mm"
    const val mYear = "yyyy"
    const val mMonth = "MM"
    const val mMonthWord = "MMMM"
    const val mDay = "dd"
    private lateinit var mFromTimestampToTitle: String

    fun getFromTimestampToTitle(currentCalDate: Calendar, mContext: Context): String{
        mFromTimestampToTitle = if (!isRussianLocalization(mContext)) {
            val dayNumberSuffix = getDayNumberSuffix(currentCalDate.get(Calendar.DAY_OF_MONTH))
            "MMMM dd'$dayNumberSuffix', EEEE"
        } else {
            "EEEE, dd MMMM"
        }
        return mFromTimestampToTitle
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