package tk.lorddarthart.justdoitlist

import android.app.Application
import tk.lorddarthart.justdoitlist.di.component.AppComponent

class JustDoItListApp: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: Application
        var component: AppComponent? = null
    }
}