package tk.lorddarthart.justdoitlist

import android.app.Application
import tk.lorddarthart.justdoitlist.di.component.AppComponent
import tk.lorddarthart.justdoitlist.di.component.DaggerAppComponent

class JustDoItListApp: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }

    companion object {
        var component: AppComponent? = null
    }
}