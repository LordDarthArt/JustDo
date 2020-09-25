package tk.lorddarthart.justdoitlist.app.component

import dagger.Component
import tk.lorddarthart.justdoitlist.app.module.AppModule
import tk.lorddarthart.justdoitlist.app.module.DataModule
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
}