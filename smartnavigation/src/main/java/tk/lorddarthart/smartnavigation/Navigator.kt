package tk.lorddarthart.smartnavigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

/** Specifications for [SmartNavigator] class. */
interface Navigator {
    /** Custom backstack of classes that realizes the [Navigator]. */
    val backStack: MutableList<String>
    /** Specifies the current realization's fragment container id to navigate in. */
    val containerId: Int

    /**
     * Function for performing the navigation in classes that realizes the [Navigator].
     *
     * @param targetFragment Fragment to navigate to;
     * @param actionType Specifies the action that should be performed;
     * @param animType Specifies the animation that should be displayed;
     * @param arguments Custom arguments for target fragment;
     */
    fun navigate(
        targetFragment: NavigatableFragment,
        actionType: NavigationActionType,
        animType: NavigationAnimType,
        arguments: Bundle? = null
    )

    /**
     * Sets up the current realization of [Navigator] (specifies the fragment manager will be used).
     *
     * @param fragmentManager [FragmentManager] that should be used for performing the navigations;
     */
    fun init(fragmentManager: FragmentManager)

    /**
     * Function for performing the removing of [NavigatableFragment] in classes that realizes the [Navigator].
     *
     * @param fragmentToRemove Fragment to remove from current fragment manager;
     */
    fun remove(fragmentToRemove: NavigatableFragment)

    /**
     * Function that performs the removing the current [NavigatableFragment]'s backStackKey from [backStack].
     *
     * @param backStackKey The specified backStackKey which should be removed;
     */
    fun removeFromBackStack(backStackKey: String)
    fun getBack()
    fun getActiveFragment(): NavigatableFragment?
    fun getActiveTab(): NavigatableFragment?
}