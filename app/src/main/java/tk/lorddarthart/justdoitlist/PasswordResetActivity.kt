package tk.lorddarthart.justdoitlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class PasswordResetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, LogInActivity::class.java))
    }
}
