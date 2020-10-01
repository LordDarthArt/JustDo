package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.model.holder.ToDoHolder
import tk.lorddarthart.justdoitlist.model.holder.ToDoHolderImpl
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideToDoListHolder(): ToDoHolder {
        return ToDoHolderImpl()
    }
}