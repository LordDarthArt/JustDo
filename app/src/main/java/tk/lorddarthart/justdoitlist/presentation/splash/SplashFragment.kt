package tk.lorddarthart.justdoitlist.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

import tk.lorddarthart.justdoitlist.presentation.splash.base.BaseSplashFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentSplashBinding
import tk.lorddarthart.justdoitlist.util.helper.logError
import java.util.concurrent.TimeUnit

class SplashFragment : BaseSplashFragment(), SplashFragmentView {
    @InjectPresenter lateinit var splashPresenter: SplashPresenter

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentSplashBinding.inflate(inflater, container, false)
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
