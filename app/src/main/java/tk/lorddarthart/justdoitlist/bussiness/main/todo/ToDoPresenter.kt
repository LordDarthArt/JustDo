package tk.lorddarthart.justdoitlist.bussiness.main.todo

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.bussiness.main.base.BaseMainPresenter
import tk.lorddarthart.justdoitlist.model.holder.ToDoHolder
import tk.lorddarthart.justdoitlist.repository.remote.RemoteRepository
import tk.lorddarthart.justdoitlist.util.helper.RequestDataCallback
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import tk.lorddarthart.justdoitlist.view.main.home.todo.ToDoFragmentView
import javax.inject.Inject

@InjectViewState
class ToDoPresenter : BaseMainPresenter<ToDoFragmentView>() {
    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var toDoHolder: ToDoHolder
    @Inject lateinit var localeHelper: LocaleHelper
    @Inject lateinit var remoteRepository: RemoteRepository

    override fun init() {
        JustDoItListApp.component?.inject(this)
    }

    fun loadData() {
        val requestDataCallback = RequestDataCallback(
            onSuccess = { viewState.showToDo() },
            onFailure = { viewState.onFailure(it) },
            onEmpty = { viewState.noTasks() }
        )
        remoteRepository.accessTheRemoteData(requestDataCallback)
    }
}