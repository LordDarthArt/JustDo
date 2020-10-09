package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.justdoitlist.router.RouterImpl
import tk.lorddarthart.smartnavigation.SmartNavigator
import javax.inject.Singleton

@Module
class RouterModule {
    @Provides
    @Singleton
    fun provideRouter(): Router {
        return RouterImpl(
            baseNavigator = SmartNavigator(R.id.base_container),
            homeNavigator = SmartNavigator(R.id.home_container),
            authNavigator = SmartNavigator(R.id.auth_container)
        )
    }
}