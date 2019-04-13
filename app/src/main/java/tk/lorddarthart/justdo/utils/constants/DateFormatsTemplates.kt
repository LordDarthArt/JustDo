package tk.lorddarthart.justdo.utils.constants

import android.content.Context
import android.os.Build
import java.util.*

object DateFormatsTemplates {
    const val mFromDatabaseToTimestamp = "dd.MM.yyyy"
    private lateinit var mFromTimestampToTitle: String
    private val locale = Locale("ru", "RU")

    fun getFromTimestampToTitle(currentCalDate: Calendar, mContext: Context): String{
        mFromTimestampToTitle = if (getLocale(mContext) != locale) {
            val dayNumberSuffix = getDayNumberSuffix(currentCalDate.get(Calendar.DAY_OF_MONTH))
            "MMMM dd'$dayNumberSuffix', EEEE"
        } else {
            "EEEE, dd MMMM"
        }
        return mFromTimestampToTitle
    }

    private fun getLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.resources.configuration.locales.get(0)
        } else{
            context.resources.configuration.locale
        }
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