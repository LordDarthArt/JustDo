package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.bussiness.Interactor
import tk.lorddarthart.justdoitlist.bussiness.InteractorImpl
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideInteractor(): Interactor {
        return InteractorImpl()
    }
}