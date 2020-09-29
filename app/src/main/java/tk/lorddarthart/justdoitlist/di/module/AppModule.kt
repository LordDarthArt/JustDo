package tk.lorddarthart.justdoitlist.di.module

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.router.IRouter
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.smartnavigation.SmartNavigator
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideRouter(): IRouter {
        return Router(
            baseNavigator = SmartNavigator(R.id.fragment_base_container),
            mainNavigator = SmartNavigator(R.id.fragment_main_container),
            authNavigator = SmartNavigator(R.id.fragment_enter)
        )
    }

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}