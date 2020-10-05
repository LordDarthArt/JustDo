package tk.lorddarthart.justdoitlist.view.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tk.lorddarthart.justdoitlist.bussiness.splash.SplashPresenter
import tk.lorddarthart.justdoitlist.databinding.SplashFragmentBinding
import tk.lorddarthart.justdoitlist.view.splash.base.BaseSplashFragment
import tk.lorddarthart.justdoitlist.util.helper.logError
import java.util.concurrent.TimeUnit

class SplashFragment : BaseSplashFragment(), SplashFragmentView {
    @InjectPresenter lateinit var splashPresenter: SplashPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = SplashFragmentBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        // do something
    }

    @SuppressLint("CheckResult")
    override fun start() {
        try {
            activity.supportActionBar?.hide()
            Single.create<() -> Unit> {
                it.onSuccess {
                    router.openNextAfterSplash()
                }
            }.subscribeOn(Schedulers.newThread())
                .delay(2, TimeUnit.SECONDS)
                .subscribe { executable ->
                    executable()
                }
        } catch (exception: Exception) {
            logError(exception) { "got exception: ${exception.message}" }
        }
    }
}
