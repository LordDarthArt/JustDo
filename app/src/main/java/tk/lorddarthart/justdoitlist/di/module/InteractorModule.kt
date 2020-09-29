package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.bussiness.local.ILocalInteractor
import tk.lorddarthart.justdoitlist.bussiness.local.LocalInteractor
import tk.lorddarthart.justdoitlist.bussiness.remote.IRemoteInteractor
import tk.lorddarthart.justdoitlist.bussiness.remote.RemoteInteractor
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideLocalInteractor(): ILocalInteractor {
        return LocalInteractor()
    }

    @Provides
    @Singleton
    fun provideRemoteInteractor(): IRemoteInteractor {
        return RemoteInteractor()
    }
}