package tk.lorddarthart.justdoitlist.application.signin

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.signin.view.SignInFragment
import tk.lorddarthart.justdoitlist.utils.OnBackPressedListener


class SignInActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var onBackPressedListener: OnBackPressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_main, SignInFragment()).commit()
    }

    override fun onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener!!.doBack()
        } else {
            if (doubleBackToExitPressedOnce) {
                this.finishAffinity()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    fun setOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener
    }
}
