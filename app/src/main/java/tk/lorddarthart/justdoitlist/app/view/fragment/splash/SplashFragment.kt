package tk.lorddarthart.justdoitlist.app.view.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.support.v4.act
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.splash.SplashFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.MainFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment(), SplashFragmentView {
    private lateinit var splashFragmentBinding: FragmentSplashBinding

    @InjectPresenter
    lateinit var splashFragmentPresenter: SplashFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        splashFragmentBinding = FragmentSplashBinding.inflate(inflater, container, false)

        try {
            activity.supportActionBar?.hide()
            Handler().postDelayed({
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, AuthFragment()).commit()
                } else {
                    activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, MainFragment()).commit()
                }
                activity.supportActionBar?.show()
                activity.supportActionBar?.elevation = 0f
            }, 2000)
        } catch (e: Exception) {
            Log.d(TAG, "got exception: ", e)
        }

        return splashFragmentBinding.root
    }
}
