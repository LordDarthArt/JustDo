package tk.lorddarthart.justdoitlist.app.view.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.coroutines.*
import tk.lorddarthart.justdoitlist.app.App

import tk.lorddarthart.justdoitlist.app.presenter.fragment.splash.SplashPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.base.BaseSplashFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSplashBinding
import tk.lorddarthart.justdoitlist.util.constants.TimeConstant.ONE_SECOND
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import javax.inject.Inject

class SplashFragment : BaseSplashFragment(), SplashFragmentView {
    @Inject lateinit var navUtils: NavUtils

    @InjectPresenter
    lateinit var splashPresenter: SplashPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentSplashBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }

    override fun initListeners() {
        // do something
    }

    override fun start() {
        App.NAV_COMPONENT.inject(this)

        try {
            activity.supportActionBar?.hide()
            CoroutineScope(Dispatchers.Main).launch {
                delay(ONE_SECOND * 2)
                navUtils.moveNextAfterSplash()
                activity.supportActionBar?.let {
                    it.show()
                    it.elevation = 0f
                }
                this.cancel()
            }
        } catch (exception: Exception) {
            logError(exception) { "got exception: " }
        }
    }
}
