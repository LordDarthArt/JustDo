package tk.lorddarthart.justdoitlist.view.root

import com.arellomobile.mvp.MvpView

interface RootActivityView: MvpView {
    fun setup()
    fun setupDependencyInjection()

    /** Configuring navigation for application to navigate correctly. */
    fun setupNavigation()
    fun setMainTitle(mainTitle: String)
    fun setActionBarTitle(title: String)
}