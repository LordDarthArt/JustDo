package tk.lorddarthart.justdoitlist.application.main.todo.add

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.main.MainFragment

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent(this, MainFragment::class.java))
    }
}
