package tk.lorddarthart.justdoitlist.util.helper.locale

import android.content.Context
import android.os.Build
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.RU_LANG_CODE
import tk.lorddarthart.justdoitlist.util.constants.StringConstant.RU_REGION_CODE
import java.util.Locale

class LocaleHelperImpl: LocaleHelper {
    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context
    }

    override fun isRussianLocalization(): Boolean {
        return getLocale() == Locale(RU_LANG_CODE, RU_REGION_CODE)
    }

    override fun getLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.resources.configuration.locales.get(0)
        } else{
            context.resources.configuration.locale
        }
    }
}