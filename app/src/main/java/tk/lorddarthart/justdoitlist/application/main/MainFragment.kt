package tk.lorddarthart.justdoitlist.application.main

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_main.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.application.main.profile.view.AccountFragment
import tk.lorddarthart.justdoitlist.application.main.todo.view.ToDoFragment
import tk.lorddarthart.justdoitlist.utils.IntentExtraConstNames
import tk.lorddarthart.justdoitlist.utils.IntentExtraConstValues

private const val TAG = "ListActivity"

class MainFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> {
                initializeFragment(ToDoFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                initializeFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_main, container, false)
        initializeFragment(ToDoFragment())
        mActivity.supportActionBar!!.title = mActivity.resources.getString(R.string.todo_title)
        if (arguments != null && arguments!!.containsKey(IntentExtraConstNames.mShowExtraNotifications)) {
            when (arguments!!.getString(IntentExtraConstNames.mShowExtraNotifications)) {
                IntentExtraConstValues.mToDoAddedSuccessfully -> {
                    Snackbar.make(mActivity.findViewById(android.R.id.content), mActivity.resources.getString(R.string.todo_added_successfully), Snackbar.LENGTH_LONG).show()
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
}
