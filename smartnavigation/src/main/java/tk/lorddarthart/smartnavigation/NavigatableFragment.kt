package tk.lorddarthart.smartnavigation

abstract class NavigatableFragment: MvpFragment() {
    abstract val backStackKey: String
}