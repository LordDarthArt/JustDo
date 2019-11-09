package tk.lorddarthart.justdoitlist.app.view.activity

import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.presenter.activity.BaseActivityPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.splash.SplashFragment
import tk.lorddarthart.justdoitlist.util.IOnBackPressedListener
import tk.lorddarthart.justdoitlist.util.listeners.OnBackPressedListener


class BaseActivity : MvpAppCompatActivity(), BaseActivityView {

    private var doubleBackToExitPressedOnce = false
    private var onBackPressedListener: OnBackPressedListener? = null
    private lateinit var mainTitle: String

    @InjectPresenter
    lateinit var baseActivityPresenter: BaseActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_main, SplashFragment()).commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_main)
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
        }
        when {
            fragment is IOnBackPressedListener -> {
                fragment.onBackPressed()
            }
            supportFragmentManager.backStackEntryCount == 0 -> {
                doubleBackToExitPressedOnce = true
                Toast.makeText(App.instance, "Press \"BACK\" again to exit", Toast.LENGTH_LONG).show()
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

    fun setOnBackPressedListener(onBackPressedListener: OnBackPressedListener?) {
        this.onBackPressedListener = onBackPressedListener
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.let {
            it.title = title
        }
    }
}
