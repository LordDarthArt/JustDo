package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import java.text.SimpleDateFormat
import javax.inject.Named
import javax.inject.Singleton

@Module
class DateFormatterModule {
    @Provides
    @Named(DateFormatsTemplates.hours)
    fun provideSimpleDateFormatHH(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.hours)
    }

    @Provides
    @Named(DateFormatsTemplates.minutes)
    fun provideSimpleDateFormatMM(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.minutes)
    }

    @Named(DateFormatsTemplates.dayTime)
    @Provides
    fun provideSimpleDateFormatHHMM(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.dayTime)
    }

    @Provides
    @Named(DateFormatsTemplates.day)
    fun provideSimpleDateFormatDD(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.day)
    }

    @Provides
    @Singleton
    @Named(DateFormatsTemplates.fromDatabaseToTimestamp)
    fun provideSimpleDateFormatDate(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.fromDatabaseToTimestamp)
    }

    @Provides
    @Named(DateFormatsTemplates.month)
    fun provideSimpleDateFormatM(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.month)
    }

    @Provides
    @Named(DateFormatsTemplates.monthWord)
    fun provideSimpleDateFormatMW(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.monthWord)
    }

    @Provides
    @Named(DateFormatsTemplates.year)
    fun provideSimpleDateFormatYYYY(): SimpleDateFormat {
        return SimpleDateFormat(DateFormatsTemplates.year)
    }
}