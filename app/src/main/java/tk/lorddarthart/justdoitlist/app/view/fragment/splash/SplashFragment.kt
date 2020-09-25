package tk.lorddarthart.justdoitlist.app.view.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import tk.lorddarthart.justdoitlist.app.presenter.fragment.splash.SplashPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.base.BaseSplashFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSplashBinding
import tk.lorddarthart.justdoitlist.util.helper.logError

class SplashFragment : BaseSplashFragment(), SplashFragmentView {
    @InjectPresenter lateinit var splashPresenter: SplashPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentSplashBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        // do something
    }

    override fun start() {
        try {
            activity.supportActionBar?.hide()
            Handler().postDelayed({
                navUtils.moveNextAfterSplash()
            }, 2000)
        } catch (exception: Exception) {
            logError(exception) { "got exception: ${exception.message}" }
        }
    }
}
