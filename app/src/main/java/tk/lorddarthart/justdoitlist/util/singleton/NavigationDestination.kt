package tk.lorddarthart.justdoitlist.util.singleton

import tk.lorddarthart.justdoitlist.app.model.pojo.other.FragmentNavigationDestination
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.ToDoFragment

object NavigationDestination {
    object AuthNavigationDestination {
        var SignInDestination = FragmentNavigationDestination(SignInFragment())
        var SignUpDestination = FragmentNavigationDestination(SignUpFragment())

        fun RefreshDestinations() {
            SignInDestination = FragmentNavigationDestination(SignInFragment())
            SignUpDestination = FragmentNavigationDestination(SignUpFragment())
        }
    }

    object MainNavigationDestination {
        var ToDoDestination = FragmentNavigationDestination(ToDoFragment())
        var AccountDestination = FragmentNavigationDestination(ProfileFragment())

        fun RefreshDestinations() {
            ToDoDestination = FragmentNavigationDestination(ToDoFragment())
            AccountDestination = FragmentNavigationDestination(ProfileFragment())
        }
    }
}