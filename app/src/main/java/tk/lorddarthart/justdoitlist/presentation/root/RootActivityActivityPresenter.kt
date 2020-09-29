package tk.lorddarthart.justdoitlist.presentation.root

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth

import tk.lorddarthart.justdoitlist.presentation.base.BaseActivityPresenter

/**
 * [BaseActivityPresenter] for [RootActivity] which holds all values and business logic.
 *
 * @author Artyom Tarasov
 */
@InjectViewState
class RootActivityActivityPresenter : BaseActivityPresenter<RootActivityView>()