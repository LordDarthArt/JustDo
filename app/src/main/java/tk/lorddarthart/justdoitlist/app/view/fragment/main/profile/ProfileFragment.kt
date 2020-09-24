package tk.lorddarthart.justdoitlist.app.view.fragment.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.profile.ProfilePresenter
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.fragment.main.base.BaseMainTabFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAccountBinding

class ProfileFragment : BaseMainTabFragment(), ProfileFragmentView {

    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun start() {
        with (fragmentBinding as FragmentAccountBinding) {
            tvUserLogIn.text = FirebaseAuth.getInstance().currentUser?.email
        }
    }

    override fun initListeners() {
        with (fragmentBinding as FragmentAccountBinding) {
            clUserLogOut.setOnClickListener {
                activity.finish()
                val intent = Intent(activity, BaseActivity::class.java)
                intent.putExtra("email", FirebaseAuth.getInstance().currentUser?.email)
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
            }
        }
    }

    companion object {
        var INSTANCE: ProfileFragment? = null
    }
}
