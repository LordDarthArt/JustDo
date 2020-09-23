package tk.lorddarthart.smartnavigation.types

sealed class NavigationAnimType {
    object FadeAnim: NavigationAnimType()
    object SlideAnim: NavigationAnimType()
    object NoAnim: NavigationAnimType()
}