package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.repository.local.LocalRepository
import tk.lorddarthart.justdoitlist.repository.local.LocalRepositoryImpl
import tk.lorddarthart.justdoitlist.repository.remote.RemoteRepository
import tk.lorddarthart.justdoitlist.repository.remote.RemoteRepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalRepository(): LocalRepository {
        return LocalRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(): RemoteRepository {
        return RemoteRepositoryImpl()
    }
}