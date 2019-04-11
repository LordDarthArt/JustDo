package tk.lorddarthart.justdoitlist.application.signin.pwreset

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.signin.SignInActivity

private const val TAG = "PasswordResetActivity"

class PasswordResetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, SignInActivity::class.java))
    }
}
