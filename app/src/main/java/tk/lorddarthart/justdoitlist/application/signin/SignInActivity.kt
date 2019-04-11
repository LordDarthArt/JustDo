package tk.lorddarthart.justdoitlist.application.signin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import tk.lorddarthart.justdoitlist.R
import android.widget.Toast
import tk.lorddarthart.justdoitlist.application.signin.login.LogInFragment
import tk.lorddarthart.justdoitlist.application.signin.view.SignInFragment


class SignInActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_main, SignInFragment()).commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
