package tk.lorddarthart.justdoitlist.bussiness.auth.additionalinfo

import com.arellomobile.mvp.InjectViewState
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.auth.base.BaseAuthPresenter
import tk.lorddarthart.justdoitlist.view.auth.additionalinfo.AdditionalInfoFragmentView
import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter

@InjectViewState
class AdditionalInfoPresenter: BaseAuthPresenter<AdditionalInfoFragmentView>() {
    override fun init() {
        JustDoItListApp.component?.inject(this)
    }
}