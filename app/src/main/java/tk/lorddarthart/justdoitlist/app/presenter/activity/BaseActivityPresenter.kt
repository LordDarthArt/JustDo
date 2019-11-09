package tk.lorddarthart.justdoitlist.app.presenter.activity

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.app.model.model.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.app.presenter.base.BasePresenter
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivityView

@InjectViewState
class BaseActivityPresenter : BasePresenter<BaseActivityView>() {
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    var toDoList = mutableListOf<ToDoItemDayModel>()
}