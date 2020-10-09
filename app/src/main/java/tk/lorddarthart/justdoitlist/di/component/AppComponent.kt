package tk.lorddarthart.justdoitlist.di.component

import dagger.Component
import tk.lorddarthart.justdoitlist.bussiness.auth.additionalinfo.AdditionalInfoPresenter
import tk.lorddarthart.justdoitlist.bussiness.auth.resetpassword.ResetPasswordPresenter
import tk.lorddarthart.justdoitlist.bussiness.auth.signin.SignInPresenter
import tk.lorddarthart.justdoitlist.bussiness.auth.signup.SignUpPresenter
import tk.lorddarthart.justdoitlist.bussiness.main.HomePresenter
import tk.lorddarthart.justdoitlist.di.module.*
import tk.lorddarthart.justdoitlist.view.auth.AuthFragment
import tk.lorddarthart.justdoitlist.view.auth.resetpassword.ResetPasswordFragment
import tk.lorddarthart.justdoitlist.view.auth.signin.SignInFragment
import tk.lorddarthart.justdoitlist.view.auth.signup.SignUpFragment
import tk.lorddarthart.justdoitlist.view.root.RootActivity
import tk.lorddarthart.justdoitlist.view.base.BaseFragment
import tk.lorddarthart.justdoitlist.view.main.home.profile.ProfileFragment
import tk.lorddarthart.justdoitlist.view.main.home.todo.ToDoFragment
import tk.lorddarthart.justdoitlist.view.main.add.AddFragment
import tk.lorddarthart.justdoitlist.view.splash.SplashFragment
import tk.lorddarthart.justdoitlist.bussiness.auth.AuthPresenter
import tk.lorddarthart.justdoitlist.view.main.home.HomeFragment
import tk.lorddarthart.justdoitlist.bussiness.main.profile.ProfilePresenter
import tk.lorddarthart.justdoitlist.bussiness.main.todo.ToDoPresenter
import tk.lorddarthart.justdoitlist.view.auth.additionalinfo.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.bussiness.main.todo.add.AddPresenter
import tk.lorddarthart.justdoitlist.bussiness.main.todo.sort.SortPresenter
import tk.lorddarthart.justdoitlist.view.main.sort.SortFragment
import tk.lorddarthart.justdoitlist.bussiness.root.RootActivityPresenter
import tk.lorddarthart.justdoitlist.bussiness.splash.SplashPresenter
import tk.lorddarthart.justdoitlist.repository.local.LocalRepositoryImpl
import tk.lorddarthart.justdoitlist.repository.remote.RemoteRepositoryImpl
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, DateFormatterModule::class, FirebaseModule::class, RepositoryModule::class, RouterModule::class])
@Singleton
interface AppComponent {
    fun inject(rootActivity: RootActivity)

    fun inject(baseFragment: BaseFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(authFragment: AuthFragment)
    fun inject(additionalInfoFragment: AdditionalInfoFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(toDoFragment: ToDoFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(resetPasswordFragment: ResetPasswordFragment)
    fun inject(addFragment: AddFragment)
    fun inject(sortFragment: SortFragment)

    fun inject(rootActivityPresenter: RootActivityPresenter)

    fun inject(signInPresenter: SignInPresenter)
    fun inject(signUpPresenter: SignUpPresenter)
    fun inject(authPresenter: AuthPresenter)
    fun inject(additionalInfoPresenter: AdditionalInfoPresenter)
    fun inject(splashPresenter: SplashPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(toDoPresenter: ToDoPresenter)
    fun inject(profilePresenter: ProfilePresenter)
    fun inject(resetPasswordPresenter: ResetPasswordPresenter)
    fun inject(addPresenter: AddPresenter)
    fun inject(sortPresenter: SortPresenter)

    fun inject(remoteRepository: RemoteRepositoryImpl)
    fun inject(localRepository: LocalRepositoryImpl)
}