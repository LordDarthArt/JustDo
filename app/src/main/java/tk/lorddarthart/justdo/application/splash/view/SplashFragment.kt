package tk.lorddarthart.justdo.application.splash.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import tk.lorddarthart.justdo.R
import tk.lorddarthart.justdo.application.BaseActivity
import tk.lorddarthart.justdo.application.main.MainFragment
import tk.lorddarthart.justdo.application.signin.view.SignInFragment
import java.lang.Exception

class SplashFragment: Fragment() {
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_splash, container, false)

        try {
            mActivity.supportActionBar?.hide()
            Handler().postDelayed({
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                if (currentUser==null) {
                    mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, SignInFragment()).commit()
                }else{
                    mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_main, MainFragment()).commit()
                }
                mActivity.supportActionBar?.show()
                mActivity.supportActionBar?.elevation = 0f}, 2000)
        } catch (e: Exception) {
            Log.d(TAG, "got exception: ", e)
        }

        return mView
    }

    companion object {

        private const val TAG = "SplashFragment"

        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}
