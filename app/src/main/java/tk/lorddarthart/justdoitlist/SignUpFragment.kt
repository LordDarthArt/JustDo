package tk.lorddarthart.justdoitlist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.util.regex.Pattern
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.apache.commons.validator.routines.EmailValidator





// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SignUpFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        container?.removeAllViews()
        view.ivSignUpHidePassword.tag = R.drawable.ic_eye_unvisible
        view.ivSignUpHidePassword.setOnClickListener {
            HidePass().clickHidePass(view.ivSignUpHidePassword, view.tvSignUpPassword)
        }
        view.ivSignUpHideConfirmPassword.tag = R.drawable.ic_eye_unvisible
        view.ivSignUpHideConfirmPassword.setOnClickListener {
            HidePass().clickHidePass(view.ivSignUpHideConfirmPassword, view.tvSignUpConfirmPassword)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if (arguments!=null && arguments!!.containsKey("email")) {
            view.tvSignUpEmail.setText(arguments!!.getString("email"))
        }
        view.btnSignUp.setOnClickListener {
            view.tilSignUpEmail.error = null
            view.tvSignUpPassword.error = null
            view.tilSignUpConfirmPassword.error = null
            val email = view.tvSignUpEmail.text.toString()
            val password = view.tvSignUpPassword.text.toString()
            val confirmpassword = view.tvSignUpConfirmPassword.text.toString()
            if (email!=""&&password!=""&&isValidEmailAddress(tvSignUpEmail.text.toString())&&isValidPassword(view.tvSignUpPassword.text.toString())&&password == confirmpassword) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                mAuth.signInWithEmailAndPassword(email, password)
                                sendVerificationEmail(view)
                            } else {
                                Snackbar.make(view, "Sign Up failed",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }
            } else {
                if (password!=confirmpassword) {
                    view.tilSignUpConfirmPassword.error = "passwords don't match"
                }
                if (!isValidEmailAddress(tvSignUpEmail.text.toString())) {
                    view.tilSignUpEmail.error = "email is not valid"
                }
                if (!isValidPassword(tvSignUpPassword.text.toString())) {
                    view.tilSignUpPassword.error = "password is not valid"
                }
                if (email=="") {
                    view.tilSignUpEmail.error = "email can't be empty"
                }
                if (password=="") {
                    view.tilSignUpPassword.error = "password can't be empty"
                }
                if (confirmpassword=="") {
                    view.tilSignUpConfirmPassword.error = "password confirmation can't be empty"
                }
            }
        }
    }

    private fun sendVerificationEmail(view: View) {
        val user = FirebaseAuth.getInstance().currentUser

        user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().signOut()
                        Snackbar.make(view, "Email verification has been sent. Please check your email",
                                Toast.LENGTH_SHORT).show()
                        fragmentManager!!.beginTransaction().replace(R.id.frEnter, LogInFragment()).commit()
                    } else {
                        Snackbar.make(view, "", Snackbar.LENGTH_LONG).show()
                    }
                }
    }

    fun isValidEmailAddress(email: String): Boolean {
        return EmailValidator.getInstance().isValid(email)
    }

    fun isValidPassword(password: String): Boolean {
        var result = false
        var capitalletters = 0
        if (password.length<8) { return result }
        else if (password.contains(" ")||password.contains("\n")) { return result }
        else {
            val digit = Pattern.compile("[0-9]")
            val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
            for (i in 0 until password.length) {
                if (password[i]==password[i].toUpperCase()) {
                    capitalletters++
                }
            }
            val hasCapitalLetters = capitalletters>=2
            val hasDigit = digit.matcher(password)
            val hasSpecial = special.matcher(password)
            result = hasCapitalLetters && hasDigit.find() && hasSpecial.find()
        }
        return result
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
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SignUpFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
