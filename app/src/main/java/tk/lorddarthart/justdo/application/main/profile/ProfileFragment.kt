package tk.lorddarthart.justdo.application.main.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*
import tk.lorddarthart.justdo.R
import tk.lorddarthart.justdo.application.main.todo.add.AddFragment
import tk.lorddarthart.justdo.application.BaseActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        container?.removeAllViews() - Maybe i don't need this???
        mView = inflater.inflate(R.layout.fragment_account, container, false)

        // TODO: do something

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.clUserLogOut.setOnClickListener{
            activity!!.finish()
            val intent = Intent(activity!!, BaseActivity::class.java)
            intent.putExtra("email", FirebaseAuth.getInstance().currentUser?.email)
            FirebaseAuth.getInstance().signOut()
            startActivity(intent)
        }
        view.tvUserLogIn.text = FirebaseAuth.getInstance().currentUser!!.email
    }

    companion object {

        private const val TAG = "ProfileFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AddFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
