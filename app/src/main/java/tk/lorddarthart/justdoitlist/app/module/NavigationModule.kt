package tk.lorddarthart.justdoitlist.app.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.smartnavigation.SmartNavigator
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationUtils(): NavUtils {
        return NavUtils(
                baseNavigator = SmartNavigator(R.id.fragment_base_container),
                mainNavigator = SmartNavigator(R.id.fragment_main_container),
                authNavigator = SmartNavigator(R.id.fragment_enter)
        )
    }
}