package tk.lorddarthart.justdoitlist.app.view.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.MainFragmentPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.additional_view.loading.LoadingFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentMainBinding
import tk.lorddarthart.justdoitlist.util.constants.DateArrays.getRussianMonthName
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.helper.Locale.isRussianLocalization
import java.text.SimpleDateFormat

class MainFragment : BaseFragment(), MainFragmentView, BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainFragmentBinding: FragmentMainBinding

    @InjectPresenter
    lateinit var mainFragmentPresenter: MainFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainFragmentBinding = FragmentMainBinding.inflate(inflater, container, false)
        initializeFragment(LoadingFragment())
        initializeActionBar()
        with(mainFragmentBinding.bottomNavigationView) {
            setOnNavigationItemSelectedListener(this@MainFragment)
            selectedItemId = R.id.navigation_main
        }
        return mainFragmentBinding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun initializeActionBar() {
        if (isRussianLocalization(activity)) {
            activity.setMainTitle(activity.resources.getString(R.string.todo_title) + " (" +
                    getRussianMonthName((SimpleDateFormat(DateFormatsTemplates.mMonth).format(System.currentTimeMillis())).toInt() - 1) + ", " +
                    SimpleDateFormat(DateFormatsTemplates.mYear).format(System.currentTimeMillis()) + ")")
        } else {
            activity.setMainTitle(activity.resources.getString(R.string.todo_title) + " (" +
                    SimpleDateFormat(DateFormatsTemplates.mMonthWord).format(System.currentTimeMillis()) + ", " +
                    SimpleDateFormat(DateFormatsTemplates.mYear).format(System.currentTimeMillis()) + ")")
        }
        activity.supportActionBar?.let {
            it.title = activity.getMainTitle()
        }
    }

    private fun initializeFragment(fr: Fragment) {
        // Initializing desired fragment
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_todo, fr)
                .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_main -> {
                initializeFragment(LoadingFragment())
                return true
            }
            R.id.navigation_account -> {
                initializeFragment(ProfileFragment())
                return true
            }
        }
        return false
    }
}
