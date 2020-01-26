package tk.lorddarthart.justdoitlist.app.presenter.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import tk.lorddarthart.justdoitlist.util.helper.Loggable

open class BasePresenter<View : MvpView> : MvpPresenter<View>(), Loggable {
}