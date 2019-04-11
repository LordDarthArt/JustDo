package tk.lorddarthart.justdoitlist.application.signin.policy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_additional_info.*
import tk.lorddarthart.justdoitlist.R

class AdditionalnfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info)
        if (intent!=null&&intent.hasExtra("act")) {
            if (intent.extras?.getString("act").equals("tc")) {
                textView3.text = resources.getString(R.string.terms_condition_txt)
            }
            if (intent.extras?.getString("act").equals("pp")) {
                textView3.text = resources.getString(R.string.privacy_policy_txt)
            }
        }
        btnAIOK.setOnClickListener { this.finish() }
    }

    override fun onBackPressed() {

    }
}
