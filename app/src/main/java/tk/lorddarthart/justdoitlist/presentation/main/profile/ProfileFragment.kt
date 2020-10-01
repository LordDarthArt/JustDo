package tk.lorddarthart.justdoitlist.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.presentation.main.base.BaseMainTabFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAccountBinding
import tk.lorddarthart.justdoitlist.presentation.auth.AuthFragment
import tk.lorddarthart.justdoitlist.presentation.base.ITab
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.util.constants.ArgumentsKeysConstant
import tk.lorddarthart.smartnavigation.NavigationTab
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class ProfileFragment : BaseMainTabFragment(), NavigationTab, ProfileFragmentView {
    @InjectPresenter lateinit var profilePresenter: ProfilePresenter

    override var INSTANCE: NavigationTab? = ProfileFragment.INSTANCE

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)
    }

    override fun start() {
        with (fragmentBinding as FragmentAccountBinding) {
            userEmail.text = FirebaseAuth.getInstance().currentUser?.email
        }
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentAccountBinding) {
            userLogOut.setOnClickListener { profilePresenter.logOut() }
        }
    }

    override fun logOut() {
        router.baseNavigator.apply {
            navigate(AuthFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
        }
    }

    companion object: NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
