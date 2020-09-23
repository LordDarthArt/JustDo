package tk.lorddarthart.justdoitlist.app.presenter.activity

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth

import tk.lorddarthart.justdoitlist.app.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.app.presenter.base.BasePresenter
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivityView

/**
 * [BasePresenter] for [BaseActivity] which holds all values and business logic.
 *
 * @author Artyom Tarasov
 */
@InjectViewState
class BaseActivityPresenter : BasePresenter<BaseActivityView>() {

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    var toDoList = mutableListOf<ToDoItemDayModel>()
}