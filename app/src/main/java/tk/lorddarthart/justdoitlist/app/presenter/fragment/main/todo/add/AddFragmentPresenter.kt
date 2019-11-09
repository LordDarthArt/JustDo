package tk.lorddarthart.justdoitlist.app.presenter.fragment.main.todo.add

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.app.presenter.fragment.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragmentView

@InjectViewState
class AddFragmentPresenter: BaseFragmentPresenter<AddFragmentView>() {
    var priority = 0L
}