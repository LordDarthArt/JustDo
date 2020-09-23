package tk.lorddarthart.justdoitlist.app

import android.app.Application
import tk.lorddarthart.justdoitlist.util.navigation.NavigationComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: Application
        lateinit var NAV_COMPONENT: NavigationComponent
    }
}