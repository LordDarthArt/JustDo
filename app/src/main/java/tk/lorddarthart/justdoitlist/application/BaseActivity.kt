package tk.lorddarthart.justdoitlist.application

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.application.signin.additionalInfo.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.application.signin.passwordreset.view.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.application.splash.view.SplashFragment
import tk.lorddarthart.justdoitlist.utils.OnBackPressedListener


class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var onBackPressedListener: OnBackPressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_main, SplashFragment()).commit()
    }

    override fun onBackPressed() {
        onBackPressedListener?.let {
            if (supportFragmentManager.fragments[supportFragmentManager.fragments.lastIndex] is ResetPasswordFragment
                    || supportFragmentManager.fragments[supportFragmentManager.fragments.lastIndex] is AddFragment) {
                it.doBack()
                onBackPressedListener = null
            }
            if (supportFragmentManager.fragments[supportFragmentManager.fragments.lastIndex] is AdditionalInfoFragment) {
                it.doBackFromAgreements()
            }
        } ?: apply {
            if (doubleBackToExitPressedOnce) {
                this.finishAffinity()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Snackbar.make(findViewById(android.R.id.content), R.string.double_press_exit, Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    fun setOnBackPressedListener(onBackPressedListener: OnBackPressedListener?) {
        this.onBackPressedListener = onBackPressedListener
    }
}
