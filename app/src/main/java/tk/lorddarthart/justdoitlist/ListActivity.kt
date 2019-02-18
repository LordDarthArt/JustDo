package tk.lorddarthart.justdoitlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> {
                initializeFragment(ListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                initializeFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar!!.title = "To Do List"
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser==null) {
            this.finish()
            startActivity(Intent(this, LogInActivity::class.java))
        }
        if (intent.hasExtra("extraShow")) {
            when (intent.getStringExtra("extraShow")) {
                "success" -> {
                    Snackbar.make(findViewById(android.R.id.content), "New TODO was successfully added", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.navigation_main

    }

    private fun initializeFragment(fr: Fragment) {
        // Initializing desired fragment
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.frToDo, fr)
        fragmentManager.commit()
    }
}
