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
        var NavComponent: NavigationComponent? = null
    }
}