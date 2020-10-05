package tk.lorddarthart.justdoitlist.view.auth

import com.arellomobile.mvp.MvpView

interface AuthFragmentView : MvpView {
    fun showSignIn()
    fun showSignUp()
    fun setSpan()
}