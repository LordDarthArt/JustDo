package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.util.converters.DayTitleConverter
import tk.lorddarthart.justdoitlist.util.converters.DayTitleConverterImpl
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverterImpl
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelperImpl
import java.util.*
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providePriorityConverter(): PriorityConverter {
        return PriorityConverterImpl()
    }

    @Provides
    @Singleton
    fun provideLocaleHelper(): LocaleHelper {
        return LocaleHelperImpl()
    }

    @Provides
    @Singleton
    fun provideDayTitleConverter(): DayTitleConverter {
        return DayTitleConverterImpl()
    }

    @Provides
    @Singleton
    fun provideCalendar(): Calendar {
        return Calendar.getInstance(TimeZone.getDefault())
    }
}