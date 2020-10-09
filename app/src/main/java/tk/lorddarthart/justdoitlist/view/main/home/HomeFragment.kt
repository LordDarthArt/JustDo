package tk.lorddarthart.justdoitlist.view.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import tk.lorddarthart.justdoitlist.JustDoItListApp
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.bussiness.main.HomePresenter
import tk.lorddarthart.justdoitlist.view.main.base.BaseMainFragment
import tk.lorddarthart.justdoitlist.databinding.HomeFragmentBinding
import tk.lorddarthart.justdoitlist.util.constants.DateArrays.getRussianMonthName
import tk.lorddarthart.justdoitlist.util.constants.DateFormatsTemplates
import tk.lorddarthart.justdoitlist.util.helper.locale.LocaleHelper
import tk.lorddarthart.justdoitlist.view.root.RootActivity
import java.text.SimpleDateFormat
import javax.inject.Inject

class HomeFragment : BaseMainFragment(), HomeFragmentView {
    @InjectPresenter lateinit var homePresenter: HomePresenter
    @Inject lateinit var localeHelper: LocaleHelper

    private val bottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_list -> { router.moveToToDoList(); true }
            R.id.navigation_profile -> { router.moveToProfile(); true }
            else -> { false }
        }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        fragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        (fragmentBinding as HomeFragmentBinding).homeNavigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    override fun start() {
        JustDoItListApp.component?.inject(this)
        router.homeNavigator.init(childFragmentManager)
        initializeActionBar()
        (fragmentBinding as HomeFragmentBinding).homeNavigation.selectedItemId = R.id.navigation_list
    }

    @SuppressLint("SimpleDateFormat")
    private fun initializeActionBar() {
        if (localeHelper.isRussianLocalization()) {
            (requireActivity() as RootActivity).setMainTitle(requireActivity().resources.getString(R.string.todo_title) + " (" +
                    getRussianMonthName((SimpleDateFormat(DateFormatsTemplates.month).format(System.currentTimeMillis())).toInt() - 1) + ", " +
                    SimpleDateFormat(DateFormatsTemplates.year).format(System.currentTimeMillis()) + ")")
        } else {
            (requireActivity() as RootActivity).setMainTitle(requireActivity().resources.getString(R.string.todo_title) + " (" +
                    SimpleDateFormat(DateFormatsTemplates.monthWord).format(System.currentTimeMillis()) + ", " +
                    SimpleDateFormat(DateFormatsTemplates.year).format(System.currentTimeMillis()) + ")")
        }
    }
}
