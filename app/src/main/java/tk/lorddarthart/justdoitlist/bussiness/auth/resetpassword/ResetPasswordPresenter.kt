package tk.lorddarthart.justdoitlist.bussiness.auth.resetpassword

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.view.auth.resetpassword.ResetPasswordFragmentView
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter

@InjectViewState
class ResetPasswordPresenter: BaseAuthPresenter<ResetPasswordFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}