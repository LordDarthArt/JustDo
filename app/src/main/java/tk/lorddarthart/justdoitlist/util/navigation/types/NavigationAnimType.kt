package tk.lorddarthart.justdoitlist.util.navigation.types

sealed class NavigationAnimType {
    object FadeAnim: NavigationAnimType()
    object SlideAnim: NavigationAnimType()
    object NoAnim: NavigationAnimType()
}