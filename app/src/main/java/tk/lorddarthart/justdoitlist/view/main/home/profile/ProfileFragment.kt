package tk.lorddarthart.justdoitlist.view.main.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.main.profile.ProfilePresenter
import tk.lorddarthart.justdoitlist.databinding.ProfileFragmentBinding
import tk.lorddarthart.justdoitlist.view.auth.AuthFragment
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainTabFragment
import tk.lorddarthart.smartnavigation.NavigationTab
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import javax.inject.Inject

class ProfileFragment : BaseMainTabFragment(), NavigationTab, ProfileFragmentView {
    @InjectPresenter lateinit var profilePresenter: ProfilePresenter
    @Inject lateinit var auth: FirebaseAuth

    override var INSTANCE: NavigationTab?
        get() { return ProfileFragment.INSTANCE }
        set(value) { ProfileFragment.INSTANCE = value }

    override fun setupNavBar(toolbar: Toolbar) {
        (fragmentBinding as ProfileFragmentBinding).profileHeadTitle.text = getString(R.string.profile)
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.title = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setupNavBar((fragmentBinding as ProfileFragmentBinding).profileHead)
        return fragmentBinding.root
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        with(fragmentBinding as ProfileFragmentBinding) {
            userLogOut.setOnClickListener { profilePresenter.logOut() }
        }
    }

    override fun start() {
        JustDoItListApp.component?.inject(this)
        profilePresenter.init()
        with(fragmentBinding as ProfileFragmentBinding) {
            userEmail.text = auth.currentUser?.email
        }
    }

    override fun logOut() {
        router.baseNavigator.navigate(AuthFragment(), NavigationActionType.ReplaceAction, NavigationAnimType.FadeAnim)
    }

    companion object : NavigationTab {
        override var INSTANCE: NavigationTab? = null
    }
}
