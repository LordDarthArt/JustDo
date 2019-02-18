package tk.lorddarthart.justdoitlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_additional_info.*

class AdditionalnfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info)
        if (intent!=null&&intent.hasExtra("act")) {
            if (intent.extras?.getString("act").equals("tc")) {
                textView3.text = resources.getString(R.string.tctxt)
            }
            if (intent.extras?.getString("act").equals("pp")) {
                textView3.text = resources.getString(R.string.pptxt)
            }
        }
        btnAIOK.setOnClickListener { this.finish() }
    }

    override fun onBackPressed() {

    }
}
