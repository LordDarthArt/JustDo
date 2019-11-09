package tk.lorddarthart.justdoitlist.app.view.fragment.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.view.fragment.main.todo.add.AddFragment
import tk.lorddarthart.justdoitlist.app.view.activity.BaseActivity
import tk.lorddarthart.justdoitlist.app.view.fragment.base.BaseFragment
import tk.lorddarthart.justdoitlist.databinding.FragmentAccountBinding

class ProfileFragment : BaseFragment() {
    private lateinit var profileFragmentBinding: FragmentAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        profileFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)

        initialization()

        return profileFragmentBinding.root
    }

    private fun initialization() {
        start()
        initListeners()
    }

    private fun start() {
        profileFragmentBinding.tvUserLogIn.text = FirebaseAuth.getInstance().currentUser!!.email
    }

    private fun initListeners() {
        profileFragmentBinding.clUserLogOut.setOnClickListener {
            activity.finish()
            val intent = Intent(activity, BaseActivity::class.java)
            intent.putExtra("email", FirebaseAuth.getInstance().currentUser?.email)
            FirebaseAuth.getInstance().signOut()
            startActivity(intent)
        }
    }
}
