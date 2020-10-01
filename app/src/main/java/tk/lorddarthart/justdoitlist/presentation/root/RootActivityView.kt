package tk.lorddarthart.justdoitlist.presentation.root

import com.arellomobile.mvp.MvpView

interface RootActivityView: MvpView {
    fun setup()
    fun setupDependencyInjection()

    /** Configuring navigation for application to navigate correctly. */
    fun setupNavigation()
}