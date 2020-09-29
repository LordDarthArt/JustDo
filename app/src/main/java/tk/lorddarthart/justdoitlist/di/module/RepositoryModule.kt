package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.repository.local.ILocalRepository
import tk.lorddarthart.justdoitlist.repository.local.LocalRepository
import tk.lorddarthart.justdoitlist.repository.remote.IRemoteRepository
import tk.lorddarthart.justdoitlist.repository.remote.RemoteRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalRepository(): ILocalRepository {
        return LocalRepository()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(): IRemoteRepository {
        return RemoteRepository()
    }
}