package tk.lorddarthart.smartnavigation.types

sealed class NavigationActionType {
    object ReplaceAction: NavigationActionType()
    object AddToBackStackAction: NavigationActionType()
    object ShowAction: NavigationActionType()
}