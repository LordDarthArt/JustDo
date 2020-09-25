package tk.lorddarthart.justdoitlist.app

import android.app.Application
import tk.lorddarthart.justdoitlist.app.component.AppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: Application
        var APP_COMPONENT: AppComponent? = null
    }
}