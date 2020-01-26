package tk.lorddarthart.justdoitlist.app.view.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import tk.lorddarthart.justdoitlist.app.presenter.fragment.splash.SplashPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.base.BaseSplashFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSplashBinding
import tk.lorddarthart.justdoitlist.util.constants.TimeConstant.ONE_SECOND
import tk.lorddarthart.justdoitlist.util.helper.logError
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.moveNextAfterSplash

class SplashFragment : BaseSplashFragment(), SplashFragmentView {
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
        try {
            actionBar?.hide()
            CoroutineScope(Dispatchers.Main).launch {
                delay(ONE_SECOND * 2)
                moveNextAfterSplash()
                with(actionBar) {
                    this?.show()
                    this?.elevation = 0f
                }
            }
        } catch (exception: Exception) {
            logError(exception) { "got exception: " }
        }
    }
}
