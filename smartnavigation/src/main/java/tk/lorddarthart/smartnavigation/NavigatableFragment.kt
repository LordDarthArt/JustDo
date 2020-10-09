package tk.lorddarthart.smartnavigation

import com.arellomobile.mvp.MvpAppCompatFragment

abstract class NavigatableFragment: MvpFragment() {
    var openedBy: SmartNavigator? = null
    abstract val backStackKey: String

    override fun onDestroy() {
        super.onDestroy()
        openedBy?.backStack?.iterator()?.let {
            it.forEach { backStackKey ->
                if (this.backStackKey == backStackKey) {
                    it.remove()
                }
            }
        }
    }
}