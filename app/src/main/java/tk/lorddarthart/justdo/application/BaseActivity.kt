package tk.lorddarthart.justdo.application

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import tk.lorddarthart.justdo.R
import tk.lorddarthart.justdo.application.main.todo.add.AddFragment
import tk.lorddarthart.justdo.application.main.todo.model.ToDoItemDayModel
import tk.lorddarthart.justdo.application.signin.additionalInfo.AdditionalInfoFragment
import tk.lorddarthart.justdo.application.signin.passwordreset.view.ResetPasswordFragment
import tk.lorddarthart.justdo.application.splash.view.SplashFragment
import tk.lorddarthart.justdo.utils.listeners.OnBackPressedListener


class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var onBackPressedListener: OnBackPressedListener? = null
    lateinit var mToDoDay: MutableList<ToDoItemDayModel>

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
