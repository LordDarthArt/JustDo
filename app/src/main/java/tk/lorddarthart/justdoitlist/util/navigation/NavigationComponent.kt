package tk.lorddarthart.justdoitlist.util.navigation

import dagger.Component
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment

@Component
interface NavigationComponent {
    fun inject(activity: BaseActivity)
    fun inject(baseFragment: BaseFragment)

    val navUtils: NavUtils
}