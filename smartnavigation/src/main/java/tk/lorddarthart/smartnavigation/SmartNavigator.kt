package tk.lorddarthart.smartnavigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class SmartNavigator(
   private val fragmentManager: FragmentManager,
   private val containerId: Int
): INavigator {
    override val backStack = mutableListOf<String>()

    override fun navigate(targetFragment: NavigatableFragment, actionType: NavigationActionType, animType: NavigationAnimType, arguments: Bundle?) {
        arguments?.let { args -> targetFragment.arguments = args }

        fragmentManager.beginTransaction().apply {
            when (actionType) {
                NavigationActionType.ReplaceAction -> {
                    if (backStack.isNotEmpty()) {
                        backStack.removeAt(backStack.lastIndex)
                    }
                    backStack.add(targetFragment.backStackKey)
                    replace(containerId, targetFragment).commitNow()
                }
                NavigationActionType.AddToBackStackAction -> {
                    backStack.add(targetFragment.backStackKey)
                    add(containerId, targetFragment).addToBackStack(getActiveTab()?.backStackKey).commit()
                }
                NavigationActionType.ShowAction -> {
                    if (!backStack.contains(targetFragment.backStackKey)) {
                        getActiveTab()?.let {
                            hide(it).add(containerId, targetFragment).commitNow()
                        } ?: apply {
                            add(containerId, targetFragment).commitNow()
                        }
                    } else {
                        getActiveTab()?.let {
                            hide(it).show(targetFragment).commitNow()
                        } ?: apply {
                            show(targetFragment).commitNow()
                        }

                        backStack.iterator().let { backStackIterator ->
                            backStackIterator.forEach {
                                if (it == targetFragment.backStackKey) {
                                    backStackIterator.remove()
                                }
                            }
                        }
                    }
                    backStack.add(targetFragment.backStackKey)
                }
            }
            when (animType) {
                NavigationAnimType.SlideAnim -> { setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left) }
                NavigationAnimType.FadeAnim -> { setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) }
                NavigationAnimType.NoAnim -> {}
            }
        }
    }

    override fun remove(fragmentToRemove: NavigatableFragment) {
        backStack.iterator().let { backStackIterator ->
            backStackIterator.forEach {
                if (it == fragmentToRemove.backStackKey) {
                    backStackIterator.remove()
                }
            }
        }

        fragmentManager.beginTransaction().remove(fragmentToRemove).commitNow()
    }

    private fun getActiveTab(): NavigatableFragment? {
        fragmentManager.fragments.forEach { fragment ->
            if (fragment is NavigatableFragment && fragment.isVisible && backStack.contains(fragment.backStackKey)) {
                return fragment
            }
        }
        return null
    }
}