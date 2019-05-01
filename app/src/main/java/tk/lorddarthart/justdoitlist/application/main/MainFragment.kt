package tk.lorddarthart.justdoitlist.application.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.application.main.view.LoadingFragment
import tk.lorddarthart.justdoitlist.utils.Locale.isRussianLocalization
import tk.lorddarthart.justdoitlist.utils.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.utils.constants.IntentExtraConstValues
import tk.lorddarthart.justdoitlist.utils.constants.DateArrays.getRussianMonthName
import java.text.SimpleDateFormat

class MainFragment : Fragment() {
    //private lateinit var auth: FirebaseAuth - Don't need this right now
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    private val TAG = javaClass.name.toString()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> {
                initializeFragment(LoadingFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                initializeFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_main, container, false)
        initializeFragment(LoadingFragment())
        if (isRussianLocalization(mActivity)) {
            mActivity.setMainTitle(mActivity.resources.getString(R.string.todo_title) + " (" +
                    getRussianMonthName((SimpleDateFormat(DateFormatsTemplates.mMonth).format(System.currentTimeMillis())).toInt() - 1) + ", " +
                    SimpleDateFormat(DateFormatsTemplates.mYear).format(System.currentTimeMillis()) + ")")
        } else {
            mActivity.setMainTitle(mActivity.resources.getString(R.string.todo_title) + " (" +
                    SimpleDateFormat(DateFormatsTemplates.mMonthWord).format(System.currentTimeMillis()) + ", " +
                    SimpleDateFormat(DateFormatsTemplates.mYear).format(System.currentTimeMillis()) + ")")
        }
        mActivity.supportActionBar?.let {
            it.title = mActivity.getMainTitle()
        }
        arguments?.let {
            if (it.containsKey(IntentExtraConstNames.mShowExtraNotifications)) {
                when (it.getString(IntentExtraConstNames.mShowExtraNotifications)) {
                    IntentExtraConstValues.mToDoAddedSuccessfully -> {
                        Snackbar.make(mActivity.findViewById(android.R.id.content), mActivity.resources.getString(R.string.todo_added_successfully), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        mView.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mView.bottomNavigationView.selectedItemId = R.id.navigation_main
        return mView
    }

    private fun initializeFragment(fr: Fragment) {
        // Initializing desired fragment
        val fragmentManager = mActivity.supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragment_todo, fr)
        fragmentManager.commit()
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
