package tk.lorddarthart.justdoitlist.presentation.auth

import com.arellomobile.mvp.MvpView

interface AuthFragmentView : MvpView {
    fun showSignIn()
    fun showSignUp()
}