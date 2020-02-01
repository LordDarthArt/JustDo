package tk.lorddarthart.justdoitlist.util.navigation

import android.os.Bundle
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.util.helper.fragmentTag
import tk.lorddarthart.justdoitlist.util.navigation.navigatable.Destination
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationActionType
import tk.lorddarthart.justdoitlist.util.navigation.types.NavigationAnimType

class CustomNavigator(private val containerId: Int = R.id.fragment_base): INavigator {
    override val currentFragment: BaseFragment?
        get() = NavUtils.NavigationFragmentManager.findFragmentById(containerId) as BaseFragment?

    override fun navigate(
            targetDestination: Destination,
            actionType: NavigationActionType,
            animType: NavigationAnimType,
            arguments: Bundle?
    ) {
        val targetFragment = targetDestination.destinationFragment
        arguments?.let { args ->
            targetFragment.arguments = args
        }

        NavUtils.NavigationFragmentManager.beginTransaction().apply {
            when (animType) {
                NavigationAnimType.SlideAnim -> { setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left) }
                NavigationAnimType.FadeAnim -> { setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) }
                NavigationAnimType.NoAnim -> {}
            }
            when (actionType) {
                NavigationActionType.ReplaceAction -> { replace(containerId, targetFragment) }
                NavigationActionType.AddToBackStackAction -> { add(containerId, targetFragment).addToBackStack(currentFragment?.fragmentTag) }
                NavigationActionType.ShowAction -> { show(targetFragment).apply { currentFragment?.let { hide(it) } } }
                NavigationActionType.JustAddAction -> { add(containerId, targetFragment).apply { currentFragment?.let { hide(it) } } }
            }
        }.commit()
    }

    override fun navigate(targetFragment: BaseFragment, actionType: NavigationActionType, animType: NavigationAnimType, arguments: Bundle?) {
        arguments?.let { args ->
            targetFragment.arguments = args
        }

        NavUtils.NavigationFragmentManager.beginTransaction().apply {
            when (animType) {
                NavigationAnimType.SlideAnim -> { setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left) }
                NavigationAnimType.FadeAnim -> { setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) }
                NavigationAnimType.NoAnim -> {}
            }
            when (actionType) {
                NavigationActionType.ReplaceAction -> { replace(containerId, targetFragment) }
                NavigationActionType.AddToBackStackAction -> { add(containerId, targetFragment).addToBackStack(currentFragment?.fragmentTag) }
                NavigationActionType.ShowAction -> { show(targetFragment).apply { currentFragment?.let { hide(it) } } }
                NavigationActionType.JustAddAction -> { add(containerId, targetFragment).apply { currentFragment?.let { hide(it) } } }
            }
        }.commit()
    }
}