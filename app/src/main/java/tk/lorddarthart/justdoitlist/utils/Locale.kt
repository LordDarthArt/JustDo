package tk.lorddarthart.justdoitlist.utils

import android.content.Context
import android.os.Build
import java.util.Locale

object Locale {
    fun isRussianLocalization(context: Context): Boolean {
        return getLocale(context) == Locale("ru","RU")
    }

    fun getLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.resources.configuration.locales.get(0)
        } else{
            context.resources.configuration.locale
        }
    }
}