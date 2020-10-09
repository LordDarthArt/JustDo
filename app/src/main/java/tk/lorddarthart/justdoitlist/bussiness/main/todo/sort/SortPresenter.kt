package tk.lorddarthart.justdoitlist.bussiness.main.todo.sort

import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.main.base.BaseMainPresenter
import tk.lorddarthart.justdoitlist.view.main.sort.SortFragmentView

class SortPresenter: BaseMainPresenter<SortFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}