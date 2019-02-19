package tk.lorddarthart.justdoitlist

import android.view.View

class PriorityConverter {
    companion object {
        fun getPriority(priority: Long?): String {
            when (priority) {
                0L -> {
                    return "Neutral"

                }
                1L -> {
                    return "Normal"
                }
                2L -> {
                    return "Important"

                }
                3L -> {
                    return "Urgently"
                }

                else -> return "Error"
            }
        }

        @Suppress("DEPRECATION")
        fun getColor(priority: String, view: View?): Int? {
            when (priority) {
                "Neutral" -> {
                    return view?.context?.resources?.getColor(R.color.emptyPriorityMarkColor)
                }
                "Normal" -> {
                    return view?.context?.resources?.getColor(R.color.lowPriorityMarkColor)
                }
                "Important" -> {
                    return view?.context?.resources?.getColor(R.color.middlePriorityMarkColor)
                }
                "Urgently" -> {
                    return view?.context?.resources?.getColor(R.color.highPriorityMarkColor)
                }
            }
            return null
        }

        fun getNumber(priority: String): Long? {
            when (priority) {
                "Neutral" -> {
                    return 0L
                }
                "Normal" -> {
                    return 1L
                }
                "Important" -> {
                    return 2L
                }
                "Urgently" -> {
                    return 3L
                }

                else -> return null
            }
        }
    }
}