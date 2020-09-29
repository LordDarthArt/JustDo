package tk.lorddarthart.justdoitlist.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import tk.lorddarthart.justdoitlist.util.helper.Loggable

abstract class BaseActivityPresenter<View : MvpView> : MvpPresenter<View>(), IBaseActivityPresenter, Loggable {
}