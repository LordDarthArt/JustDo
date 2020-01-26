package tk.lorddarthart.justdoitlist.app.view.activity

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.presenter.activity.BaseActivityPresenter
import tk.lorddarthart.justdoitlist.util.IOnBackPressedListener
import tk.lorddarthart.justdoitlist.util.helper.longSnackbar
import tk.lorddarthart.justdoitlist.util.navigation.CustomNavigator
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.baseNavigator

/**
 * Base & Single [MvpAppCompatActivity] for JustDoItList application. Implements
 * [IOnBackPressedListener].
 *
 * @author Artyom Tarasov
 */
class BaseActivity : MvpAppCompatActivity(), BaseActivityView {

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
        setupNavigation()
    }

    /** Configuring navigation for application to navigate correctly. */
    private fun setupNavigation() {
        with (NavUtils) {
            baseNavigator = CustomNavigator()
            fragmentManager = supportFragmentManager
            openSplash()
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
        }
        when {
            baseActivityPresenter.currentFragment is IOnBackPressedListener -> {
                (baseActivityPresenter.currentFragment as IOnBackPressedListener).onBackPressed()
            }
            supportFragmentManager.backStackEntryCount == 0 -> {
                doubleBackToExitPressedOnce = true
                findViewById<View>(android.R.id.content).longSnackbar { App.instance.getString(R.string.press_back_twice) }
                GlobalScope.launch(Dispatchers.IO) {
                    delay(2000L)
                    doubleBackToExitPressedOnce = false
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    fun setMainTitle(mainTitle: String) {
        this.mainTitle = mainTitle
    }

    fun getMainTitle(): String? {
        return if (::mainTitle.isInitialized) {
            mainTitle
        } else {
            null
        }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.let {
            it.title = title
        }
    }
}
