package tk.lorddarthart.justdoitlist.utils.converters

import java.text.SimpleDateFormat
import java.util.*

class DayTitleConverter(var mDateFormatTemplate: SimpleDateFormat, var mDate: Date) {
    private lateinit var mDateString: String

    fun convertToPreferred(): String {
        mDateString = mDateFormatTemplate.format(mDate)
        mDateString = mDateString.substring(0, 1).toUpperCase() + mDateString.substring(1).toLowerCase()
        if (mDateString.contains(" 0")) {
            mDateString = mDateString.replace(" 0", " ")
        }
        return mDateString
    }
}