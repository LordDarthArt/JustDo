package tk.lorddarthart.justdoitlist.presentation.root

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.databinding.ActivityBaseBinding
import tk.lorddarthart.justdoitlist.di.component.DaggerAppComponent
import tk.lorddarthart.justdoitlist.router.Router
import tk.lorddarthart.justdoitlist.util.OnBackPressable
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.util.helper.longSnackbar
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

    private var doubleBackToExitPressedOnce = false
    private lateinit var mainTitle: String
    private var activityBinding by Delegates.notNull<ActivityBaseBinding>()

    @InjectPresenter
    lateinit var rootActivityPresenter: RootActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)

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
        with (router) {
            clearBackStack()

            baseNavigator.init(supportFragmentManager)

            openSplash()
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
        }
        when {
            router.authNavigator.getActiveTab() is OnBackPressable -> { (router.authNavigator.getActiveTab() as OnBackPressable).onBackPressed() }
            router.mainNavigator.getActiveTab() is OnBackPressable -> { (router.mainNavigator.getActiveTab() as OnBackPressable).onBackPressed() }
            router.baseNavigator.getActiveFragment() is OnBackPressable -> { (router.baseNavigator.getActiveFragment() as OnBackPressable).onBackPressed() }
            supportFragmentManager.backStackEntryCount == 0 -> {
                doubleBackToExitPressedOnce = true
                activityBinding.root.longSnackbar { getString(R.string.press_back_twice) }
                Single.create<() -> Unit> { it.onSuccess { doubleBackToExitPressedOnce = false } }.subscribeOn(Schedulers.newThread())
                    .delay(2, TimeUnit.SECONDS)
                    .subscribe { executable ->
                        executable()
                    }
                }
            else -> { super.onBackPressed() }
        }
    }

    fun setMainTitle(mainTitle: String) {
        this.mainTitle = mainTitle
    }

    fun getMainTitle(): String? {
        return if (::mainTitle.isInitialized) { mainTitle } else { null }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.let {
            it.title = title
        }
    }
}
