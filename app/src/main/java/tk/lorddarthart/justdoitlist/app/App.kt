package tk.lorddarthart.justdoitlist.app

import android.app.Application
import android.content.Context
import tk.lorddarthart.justdoitlist.util.navigation.CustomNavigator

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var activityContext: Context
        lateinit var instance: Application
        lateinit var navigator: CustomNavigator
    }
}