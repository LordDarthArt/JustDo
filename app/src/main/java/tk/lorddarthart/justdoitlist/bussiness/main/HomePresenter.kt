package tk.lorddarthart.justdoitlist.bussiness.main

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.view.main.home.HomeFragmentView

@InjectViewState
class HomePresenter : BaseFragmentPresenter<HomeFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}