package tk.lorddarthart.justdoitlist.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.presentation.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentMainBinding
import tk.lorddarthart.justdoitlist.databinding.FragmentSignUpBinding
import tk.lorddarthart.justdoitlist.util.constants.DateArrays.getRussianMonthName
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.helper.LocaleHelper.isRussianLocalization
import java.text.SimpleDateFormat

class MainFragment : BaseMainFragment(), MainFragmentView {
    @InjectPresenter lateinit var mainPresenter: MainPresenter

    private val bottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> { router.moveToToDoList(); true }
            R.id.navigation_account -> { router.moveToProfile(); true }
            else -> { false }
        }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        (fragmentBinding as FragmentMainBinding).fragmentMainBottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    override fun start() {
        initializeActionBar()
        (fragmentBinding as FragmentMainBinding).fragmentMainBottomNavigationView.selectedItemId = R.id.navigation_main
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
}
