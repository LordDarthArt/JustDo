package tk.lorddarthart.justdoitlist.application.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.signin.SignInActivity
import tk.lorddarthart.justdoitlist.application.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler().postDelayed({
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            if (currentUser==null) {
                this.finish()
                startActivity(Intent(this, SignInActivity::class.java))
            }else{
                this.finish()
                startActivity(Intent(this, MainActivity::class.java))
            }}, 2000)
    }
}
