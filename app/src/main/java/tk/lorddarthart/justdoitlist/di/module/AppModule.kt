package tk.lorddarthart.justdoitlist.di.module

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.justdoitlist.router.RouterImpl
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverterImpl
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelperImpl
import tk.lorddarthart.smartnavigation.SmartNavigator
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
}