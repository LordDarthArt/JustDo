package tk.lorddarthart.justdoitlist.util.constants

object DateArrays {
    private val monthNames = arrayOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
    private val mDays = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")

    fun getRussianMonthName(i: Int): String {
        return monthNames[i]
    }

    fun getDay(i: Int): String {
        return mDays[i]
    }
}