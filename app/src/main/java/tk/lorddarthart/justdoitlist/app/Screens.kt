package tk.lorddarthart.justdoitlist.app

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.AuthFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.sign_up.SignUpFragment

object Screens {
    object AuthScreen: SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AuthFragment()
        }

        object LogInScreen: SupportAppScreen() {
            override fun getFragment(): Fragment {
                return SignInFragment()
            }
        }

        object SignUpScreen: SupportAppScreen() {
            override fun getFragment(): Fragment {
                return SignUpFragment()
            }
        }
    }

    object MainScreen: SupportAppScreen()  {

    }

    object SplashScreen: SupportAppScreen()
}