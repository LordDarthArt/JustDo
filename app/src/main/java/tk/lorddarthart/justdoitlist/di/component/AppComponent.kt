package tk.lorddarthart.justdoitlist.di.component

import dagger.Component
import tk.lorddarthart.justdoitlist.di.module.AppModule
import tk.lorddarthart.justdoitlist.di.module.DataModule
import tk.lorddarthart.justdoitlist.di.module.InteractorModule
import tk.lorddarthart.justdoitlist.di.module.RepositoryModule
import tk.lorddarthart.justdoitlist.presentation.auth.AuthFragment
import tk.lorddarthart.justdoitlist.presentation.auth.reset_password.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.presentation.auth.sign_in.SignInFragment
import tk.lorddarthart.justdoitlist.presentation.auth.sign_up.SignUpFragment
import tk.lorddarthart.justdoitlist.presentation.root.RootActivity
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment
import tk.lorddarthart.justdoitlist.presentation.main.MainFragment
import tk.lorddarthart.justdoitlist.presentation.main.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.presentation.main.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.presentation.splash.SplashFragment
import tk.lorddarthart.justdoitlist.presentation.splash.base.BaseSplashFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, InteractorModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: RootActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(authFragment: AuthFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(homeFragment: MainFragment)
    fun inject(toDoFragment: ToDoFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(resetPasswordFragment: ResetPasswordFragment)
}