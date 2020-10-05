package tk.lorddarthart.justdoitlist.bussiness.auth.signin

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.view.auth.signin.SignInFragmentView
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter

@InjectViewState
class SignInPresenter: BaseAuthPresenter<SignInFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}