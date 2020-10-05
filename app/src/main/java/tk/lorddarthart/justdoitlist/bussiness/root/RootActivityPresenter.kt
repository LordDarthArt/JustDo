package tk.lorddarthart.justdoitlist.bussiness.root

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.base.BaseActivityPresenter
import tk.lorddarthart.justdoitlist.view.root.RootActivityView

/**
 * [BaseActivityPresenter] for [RootActivity].
 *
 * @author Artyom Tarasov
 */
@InjectViewState
class RootActivityPresenter : BaseActivityPresenter<RootActivityView>() {
    var doubleBackToExitPressedOnce = false

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}