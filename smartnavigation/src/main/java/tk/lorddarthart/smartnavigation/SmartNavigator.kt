package tk.lorddarthart.smartnavigation

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

class SmartNavigator(
   private val containerId: Int
): INavigator {
    private lateinit var fragmentManager: FragmentManager
    override val backStack = mutableListOf<String>()

    override fun init(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    override fun navigate(targetFragment: NavigatableFragment, actionType: NavigationActionType, animType: NavigationAnimType, arguments: Bundle?) {
        if (backStack.isEmpty() || backStack.last() != targetFragment.backStackKey) {
            arguments?.let { args -> targetFragment.arguments = args }

            fragmentManager.beginTransaction().apply {
                when (animType) {
                    NavigationAnimType.SlideAnim -> { setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left) }
                    NavigationAnimType.FadeAnim -> { setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out) }
                    NavigationAnimType.NoAnim -> {}
                }
                when (actionType) {
                    NavigationActionType.ReplaceAction -> {
                        if (backStack.isNotEmpty()) { backStack.remove(backStack.last()) }
                        backStack.add(targetFragment.backStackKey)
                        replace(containerId, targetFragment).commit()
                    }
                    NavigationActionType.AddToBackStackAction -> {
                        backStack.add(targetFragment.backStackKey)
                        add(containerId, targetFragment).addToBackStack(getActiveTab()?.backStackKey).commit()
                    }
                    NavigationActionType.ShowAction -> {
                        if (!backStack.contains(targetFragment.backStackKey)) {
                            getActiveTab()?.let {
                                hide(it).add(containerId, targetFragment).commit()
                            } ?: apply {
                                add(containerId, targetFragment).commit()
                            }
                        } else {
                            getActiveTab()?.let {
                                hide(it).show(targetFragment).commit()
                            } ?: apply {
                                show(targetFragment).commit()
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

    override fun removeFromBackStack(backStackKey: String) {
        backStack.iterator().let { backStackKeyIterator ->
            backStackKeyIterator.forEach { key ->
                if (backStackKey == key) {
                    backStackKeyIterator.remove()
                }
            }
        }
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