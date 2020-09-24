package tk.lorddarthart.justdoitlist.app.view.fragment.auth

import com.arellomobile.mvp.MvpView

interface AuthFragmentView : MvpView {
    fun showSignIn()
    fun showSignUp()
}