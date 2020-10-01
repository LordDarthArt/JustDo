package tk.lorddarthart.justdoitlist.presentation.root

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth

import tk.lorddarthart.justdoitlist.presentation.base.BaseActivityPresenter

/**
 * [BaseActivityPresenter] for [RootActivity].
 *
 * @author Artyom Tarasov
 */
@InjectViewState
class RootActivityPresenter : BaseActivityPresenter<RootActivityView>()