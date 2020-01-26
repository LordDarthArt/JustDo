package tk.lorddarthart.justdoitlist.util.navigation.types

sealed class NavigationActionType {
    object ReplaceAction: NavigationActionType()
    object AddToBackStackAction: NavigationActionType()
    object ShowAction: NavigationActionType()
}