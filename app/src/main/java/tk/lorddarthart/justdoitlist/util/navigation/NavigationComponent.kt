package tk.lorddarthart.justdoitlist.util.navigation

import dagger.Component
import tk.lorddarthart.justdoitlist.app.module.NavigationModule
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class])
interface NavigationComponent {
    fun inject(activity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
}