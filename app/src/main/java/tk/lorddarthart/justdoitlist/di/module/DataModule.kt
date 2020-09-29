package tk.lorddarthart.justdoitlist.di.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.model.holder.ToDoListHolder
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideToDoListHolder(): ToDoListHolder {
        return ToDoListHolder()
    }
}