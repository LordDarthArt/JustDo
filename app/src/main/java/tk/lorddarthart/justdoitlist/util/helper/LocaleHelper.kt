package tk.lorddarthart.justdoitlist.util.helper

import android.content.Context
import android.os.Build
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.RU_LANG_CODE
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.RU_REGION_CODE
import java.util.Locale

object LocaleHelper {
    fun isRussianLocalization(context: Context): Boolean {
        return getLocale(context) == Locale(RU_LANG_CODE, RU_REGION_CODE)
    }

    fun getLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.resources.configuration.locales.get(0)
        } else{
            context.resources.configuration.locale
        }
    }
}