package tk.lorddarthart.justdoitlist.app.module

import dagger.Module
import dagger.Provides
import tk.lorddarthart.justdoitlist.app.model.holder.ToDoListHolder
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideToDoListHolder(): ToDoListHolder {
        return ToDoListHolder()
    }
}