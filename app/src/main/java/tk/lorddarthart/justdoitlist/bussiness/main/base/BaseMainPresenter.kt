package tk.lorddarthart.justdoitlist.bussiness.main.base

import tk.lorddarthart.justdoitlist.bussiness.base.BaseFragmentPresenter
import tk.lorddarthart.justdoitlist.util.appsection.Main
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainView

abstract class BaseMainPresenter<View: BaseMainView>: BaseFragmentPresenter<View>(), Main