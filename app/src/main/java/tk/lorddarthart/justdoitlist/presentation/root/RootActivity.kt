package tk.lorddarthart.justdoitlist.presentation.root

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.App
import tk.lorddarthart.justdoitlist.di.component.DaggerAppComponent
import tk.lorddarthart.justdoitlist.router.IRouter
import tk.lorddarthart.justdoitlist.util.IOnBackPressedListener
import tk.lorddarthart.justdoitlist.router.Router
import javax.inject.Inject

/**
 * Base & Single [MvpAppCompatActivity] for JustDoItList application. Implements
 * [IOnBackPressedListener].
 *
 * @author Artyom Tarasov
 */
class RootActivity : MvpAppCompatActivity(), RootActivityView {
    @Inject lateinit var router: IRouter
    private var doubleBackToExitPressedOnce = false
    private lateinit var mainTitle: String

    @InjectPresenter
    lateinit var rootActivityPresenter: RootActivityActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()

        setContentView(R.layout.activity_base)
    }

    private fun setup() {
        App.component = DaggerAppComponent.create()
        App.component?.inject(this)

        setupNavigation()
    }

    /** Configuring navigation for application to navigate correctly. */
    private fun setupNavigation() {
        with (router) {
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