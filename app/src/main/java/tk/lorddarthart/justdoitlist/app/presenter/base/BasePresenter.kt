package tk.lorddarthart.justdoitlist.app.presenter.base

import androidx.lifecycle.ViewModel
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

open class BasePresenter<View: MvpView>: MvpPresenter<View>() {
}