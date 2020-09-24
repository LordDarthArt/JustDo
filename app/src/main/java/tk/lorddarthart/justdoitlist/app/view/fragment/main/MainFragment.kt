package tk.lorddarthart.justdoitlist.app.view.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.presenter.fragment.main.MainPresenter
import tk.lorddarthart.justdoitlist.app.view.fragment.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentMainBinding
import tk.lorddarthart.justdoitlist.util.constants.DateArrays.getRussianMonthName
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.helper.LocaleHelper.isRussianLocalization
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import java.text.SimpleDateFormat
import javax.inject.Inject
class MainFragment : BaseMainFragment(), MainFragmentView {
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding = FragmentMainBinding.inflate(inflater, container, false)

        initialization()

        return fragmentBinding.root
    }


    override fun initListeners() {
        (fragmentBinding as FragmentMainBinding).fragmentMainBottomNavigationView.setOnNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.navigation_main -> {
                    navUtils.moveToToDoList()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_account -> {
                    navUtils.moveToProfile()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    override fun start() {
        navUtils.showLoading()
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
