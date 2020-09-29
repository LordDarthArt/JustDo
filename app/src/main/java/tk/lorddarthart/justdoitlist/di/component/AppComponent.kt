package tk.lorddarthart.justdoitlist.di.component

import dagger.Component
import tk.lorddarthart.justdoitlist.di.module.AppModule
import tk.lorddarthart.justdoitlist.di.module.DataModule
import tk.lorddarthart.justdoitlist.di.module.InteractorModule
import tk.lorddarthart.justdoitlist.di.module.RepositoryModule
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, InteractorModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: RootActivity)
    fun inject(baseFragment: BaseFragment)
}