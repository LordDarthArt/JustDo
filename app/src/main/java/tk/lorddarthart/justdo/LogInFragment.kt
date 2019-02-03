package tk.lorddarthart.justdo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import org.apache.commons.validator.routines.EmailValidator


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LogInFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LogInFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LogInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        container?.removeAllViews()
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.tvLogInFrgt?.setOnClickListener {
            val intent = Intent(activity, PasswordResetActivity::class.java)
            if (view.tvLogInEmail.text!=null && view.tvLogInEmail.text.toString() != "") {
                intent.putExtra("email", view.tvLogInEmail.text)
            }
            startActivity(intent)
        }
        if (activity!!.intent.hasExtra("email")) {
            view.tvLogInEmail.setText(activity!!.intent.getStringExtra("email"))
        }
        view.btnLogIn.setOnClickListener {
            val email = view.tvLogInEmail.text.toString()
            val password = view.tvLogInPassword.text.toString()
            if (email!=""&&password!="") {
                view.tilLogInEmail.error = null
                view.tilLogInPassword.error = null
                if (!isValidEmailAddress(view.tvLogInEmail.text.toString())) {
                    view.tilLogInEmail.error = "email is not valid"
                }
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(activity!!, ListActivity::class.java))
                            } else {
                                    Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            } else {
                if (email=="") {
                    view.tilLogInEmail.error = "email must not be empty"
                }
                if (password=="") {
                    view.tilLogInPassword.error = "password must not be empty"
                }
            }
        }
    }

    fun isValidEmailAddress(email: String): Boolean {
        return EmailValidator.getInstance().isValid(email)
    }

    override fun onResume() {
        super.onResume()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LogInFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
