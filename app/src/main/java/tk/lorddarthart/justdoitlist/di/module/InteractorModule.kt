package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.bussiness.IInteractor
import tk.lorddarthart.justdoitlist.bussiness.Interactor
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideLocalInteractor(): IInteractor {
        return Interactor()
    }
}