package tk.lorddarthart.justdo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class ListActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar!!.title = "To Do List"
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser==null) {
            this.finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
