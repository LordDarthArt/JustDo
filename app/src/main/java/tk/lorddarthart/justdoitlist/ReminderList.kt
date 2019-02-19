package tk.lorddarthart.justdoitlist

class ReminderList {
    fun getReminderList(): List<String>{
        val list = ArrayList<String>()
        list.add("5m")
        list.add("30m")
        list.add("1h")
        list.add("1d")
        return list
    }

    fun getReminderMillis(rem: String): Long {
        when (rem) {
            "5m" -> return 300000
            "30m" -> return 1800000
            "1h" -> return 3600000
            "1d" -> return 86400000
        }
        return 0
    }

    fun getListCase(rem: Int): String {
        when (rem) {
            1 -> return "5m"
            2 -> return "30m"
            3 -> return "1h"
            4 -> return "1d"
        }
        return ""
    }
}