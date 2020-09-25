package tk.lorddarthart.justdoitlist.app.view.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.component.DaggerAppComponent
import tk.lorddarthart.justdoitlist.app.module.AppModule
import tk.lorddarthart.justdoitlist.app.module.DataModule
import tk.lorddarthart.justdoitlist.app.presenter.activity.BaseActivityPresenter
import tk.lorddarthart.justdoitlist.util.IOnBackPressedListener
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import javax.inject.Inject

/**
 * Base & Single [MvpAppCompatActivity] for JustDoItList application. Implements
 * [IOnBackPressedListener].
 *
 * @author Artyom Tarasov
 */
class BaseActivity : MvpAppCompatActivity(), BaseActivityView {
    @Inject lateinit var navUtils: NavUtils
    private var doubleBackToExitPressedOnce = false
    private lateinit var mainTitle: String

    @InjectPresenter
    lateinit var baseActivityPresenter: BaseActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()

        setContentView(R.layout.activity_base)
    }

    private fun setup() {
        App.APP_COMPONENT = DaggerAppComponent.create()
        App.APP_COMPONENT?.inject(this)

        setupNavigation()
    }

    /** Configuring navigation for application to navigate correctly. */
    private fun setupNavigation() {
        with (navUtils) {
            baseNavigator.init(supportFragmentManager)
            mainNavigator.init(supportFragmentManager)
            authNavigator.init(supportFragmentManager)
            openSplash()
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
        }
        when {
//            baseActivityPresenter.currentFragment is IOnBackPressedListener -> {
//                (baseActivityPresenter.currentFragment as IOnBackPressedListener).onBackPressed()
//            }
//            supportFragmentManager.backStackEntryCount == 0 -> {
//                doubleBackToExitPressedOnce = true
//                findViewById<View>(android.R.id.content).longSnackbar { App.instance.getString(R.string.press_back_twice) }
//                GlobalScope.launch(Dispatchers.IO) {
//                    delay(2000L)
//                    doubleBackToExitPressedOnce = false
//                }
//            }
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
