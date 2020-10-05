package tk.lorddarthart.justdoitlist.util.helper.locale

import android.content.Context
import java.util.*

interface LocaleHelper {
    fun isRussianLocalization(): Boolean
    fun getLocale(): Locale
    fun init(context: Context)
}