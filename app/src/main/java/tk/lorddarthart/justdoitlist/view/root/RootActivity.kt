package tk.lorddarthart.justdoitlist.view.root

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.bussiness.root.RootActivityPresenter
import tk.lorddarthart.justdoitlist.databinding.RootActivityBinding
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.justdoitlist.util.OnBackPressable
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Base & Single [MvpAppCompatActivity] for JustDoItList application. Implements
 * [OnBackPressable].
 *
 * @author Artyom Tarasov
 */
class RootActivity : MvpAppCompatActivity(), RootActivityView {
    @Inject lateinit var router: Router
    @Inject lateinit var priorityConverter: PriorityConverter
    @Inject lateinit var localeHelper: LocaleHelper

    private lateinit var mainTitle: String
    private var activityBinding by Delegates.notNull<RootActivityBinding>()

    @InjectPresenter lateinit var rootActivityPresenter: RootActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.root_activity)

        setup()
    }

    override fun setup() {
        setupDependencyInjection()
        setupNavigation()

        priorityConverter.init(this)
    }

    override fun setupDependencyInjection() {
        JustDoItListApp.component?.inject(this)
    }

    override fun setupNavigation() {
        localeHelper.init(this@RootActivity)
        with (router) {
            clearBackStack()

            baseNavigator.init(supportFragmentManager)

            openSplash()
        }
    }

    override fun onBackPressed() {
        if (rootActivityPresenter.doubleBackToExitPressedOnce) {
            finishAffinity()
        }
        with (router) {
            when {
                authNavigator.getActiveTab() is OnBackPressable -> { (router.authNavigator.getActiveTab() as OnBackPressable).onBackPressed() }
                mainNavigator.getActiveTab() is OnBackPressable -> { (router.mainNavigator.getActiveTab() as OnBackPressable).onBackPressed() }
                baseNavigator.getActiveFragment() is OnBackPressable -> { (router.baseNavigator.getActiveFragment() as OnBackPressable).onBackPressed() }
                supportFragmentManager.backStackEntryCount == 0 -> {
                    rootActivityPresenter.doubleBackToExitPressedOnce = true
                    activityBinding.root.longSnackbar { getString(R.string.press_back_twice) }
                    Single.create<() -> Unit> { it.onSuccess { rootActivityPresenter.doubleBackToExitPressedOnce = false } }.subscribeOn(Schedulers.newThread())
                        .delay(2, TimeUnit.SECONDS)
                        .subscribe { executable ->
                            executable()
                        }
                }
                else -> { super.onBackPressed() }
            }
        }
    }

    override fun setMainTitle(mainTitle: String) {
        this.mainTitle = mainTitle
    }

    override fun setActionBarTitle(title: String) {
        supportActionBar?.let {
            it.title = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { onBackPressed(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
