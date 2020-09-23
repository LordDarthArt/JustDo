package tk.lorddarthart.smartnavigation

import android.os.Bundle
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType

/** Specifications for [SmartNavigator] class. */
interface INavigator {

    /**
     * Function for performing the navigation in classes that realizes the [INavigator].
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
     * Function for performing the removing of [NavigatableFragment] in classes that realizes the [INavigator].
     *
     * @param fragmentToRemove Fragment to remove from current fragment manager;
     */
    fun remove(
        fragmentToRemove: NavigatableFragment
    )

    /** Custom backstack of classes that realizes the [INavigator]. */
    val backStack: MutableList<String>
}