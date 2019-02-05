package tk.lorddarthart.justdo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.android.synthetic.main.fragment_reset_password_enter.view.*
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ResetPasswordEnterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ResetPasswordEnterFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ResetPasswordEnterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // TODO: need to parse Code from URL for Password Reseting
        container?.removeAllViews()
        return inflater.inflate(R.layout.fragment_reset_password_enter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btnChangePassword.setOnClickListener {
            if (view.tvResetPasswordEnterCode.text.toString()!=""&&view.tvResetPasswordEnterPassword.text.toString()!=""&&view.tvResetPasswordEnterConfirmPassword.text.toString()!=""&&view.tvResetPasswordEnterConfirmPassword.text.toString()!=view.tvResetPasswordEnterPassword.text.toString()&&isValidPassword(view.tvResetPasswordEnterPassword.text.toString())&&FirebaseAuth.getInstance().verifyPasswordResetCode(view.tvResetPasswordEnterCode.text.toString()).isSuccessful) {
                FirebaseAuth.getInstance().confirmPasswordReset(view.tvResetPasswordEnterCode.text.toString(), view.tvResetPasswordEnterPassword.text.toString())
                Snackbar.make(view, "Password changed successfully", Snackbar.LENGTH_LONG)
                activity!!.finish()
            } else {
                if (view.tvResetPasswordEnterConfirmPassword.text.toString()==view.tvResetPasswordEnterPassword.text.toString()) {
                    view.tilResetPasswordEnterConfirmPassword.error = "Passwords don't match"
                }
                if (!isValidPassword(view.tvResetPasswordEnterPassword.text.toString())) {
                    view.tvResetPasswordEnterPassword.error = "Password is not valid"
                }
                if (!FirebaseAuth.getInstance().verifyPasswordResetCode(view.tvResetPasswordEnterCode.text.toString()).isSuccessful) {
                    view.tilResetPasswordEnterCode.error = "Code is not valid"
                }
                if (view.tvResetPasswordEnterCode.text.toString()=="") {
                    view.tilResetPasswordEnterCode.error = "Code can't be empty"
                }
                if (view.tvResetPasswordEnterPassword.text.toString()=="") {
                    view.tilResetPasswordEnterPassword.error = "Password can't be empty"
                }
                if (view.tvResetPasswordEnterConfirmPassword.text.toString()=="") {
                    view.tilResetPasswordEnterConfirmPassword.error = "Password confirmation can't be empty"
                }
            }
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
         * @return A new instance of fragment ResetPasswordEnterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ResetPasswordEnterFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
