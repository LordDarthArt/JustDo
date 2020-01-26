package tk.lorddarthart.justdoitlist.util.navigation

import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.util.helper.fragmentTag
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils.fragmentManager
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

class CustomNavigator(private val containerId: Int = R.id.fragment_base): INavigator {
    private var backUpFragment: BaseFragment? = null
    override val currentFragment: BaseFragment?
        get() {
            return if (fragmentManager.findFragmentById(containerId) != null && fragmentManager.findFragmentById(containerId)!!.isVisible) {fragmentManager.findFragmentById(containerId) as BaseFragment } else { backUpFragment }
        }

    override fun navigate(
            targetFragment: BaseFragment,
            actionType: NavigationActionType,
            animType: NavigationAnimType,
            arguments: Bundle?
    ) {
        arguments?.let { args ->
            targetFragment.arguments = args
        }

        fragmentManager.beginTransaction().apply {
            when (animType) {
                NavigationAnimType.SlideAnim -> { setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left) }
                NavigationAnimType.FadeAnim -> { setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) }
                NavigationAnimType.NoAnim -> {}
            }
            when (actionType) {
                NavigationActionType.ReplaceAction -> { replace(containerId, targetFragment) }
                NavigationActionType.AddToBackStackAction -> { add(containerId, targetFragment).addToBackStack(currentFragment!!.fragmentTag) }
                NavigationActionType.ShowAction -> { show(targetFragment).hide(currentFragment!!) }
            }
        }.commit()
    }
}