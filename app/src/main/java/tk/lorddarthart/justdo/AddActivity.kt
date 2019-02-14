package tk.lorddarthart.justdo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, ListActivity::class.java))
    }
}
