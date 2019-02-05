package tk.lorddarthart.justdo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


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
@Suppress("DEPRECATION")
class EnterFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_enter, container, false)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUpMS)
        val btnLogIn = view.findViewById<Button>(R.id.btnLogInMS)
        val txtTermsConditions = view.findViewById<TextView>(R.id.textView2)
        val txtPrivacyPolicy = view.findViewById<TextView>(R.id.textView8)
        var fragment: Fragment?
        btnSignUp.setOnClickListener {
            fragment = SignUpFragment()
            val bundle = Bundle()
            if (childFragmentManager.fragments[0].view?.tvLogInEmail?.text.toString()!="") {
                bundle.putString("email", childFragmentManager.fragments[0].view?.tvLogInEmail?.text.toString())
            }
            fragment!!.arguments = bundle
            fragmentManager!!.beginTransaction().replace(R.id.frEnter, fragment!!).commit()
            btnLogIn.setTextColor(view.resources.getColor(R.color.txtDisColor))
            btnSignUp.setTextColor(view.resources.getColor(R.color.txtColor))
        }
        btnLogIn.setOnClickListener {
            fragment = LogInFragment()
            val bundle = Bundle()
            if (childFragmentManager.fragments[0].view?.tvSignUpEmail?.text.toString()!="") {
                bundle.putString("email", childFragmentManager.fragments[0].view?.tvSignUpEmail?.text.toString())
            }
            fragment!!.arguments = bundle
            fragmentManager!!.beginTransaction().replace(R.id.frEnter, fragment!!).commit()
            btnSignUp.setTextColor(view.resources.getColor(R.color.txtDisColor))
            btnLogIn.setTextColor(view.resources.getColor(R.color.txtColor))
        }
        txtTermsConditions.setOnClickListener {
            val intent = Intent(activity, AdditionalnfoActivity::class.java)
            intent.putExtra("act","tc")
            startActivity(intent)
        }
        txtPrivacyPolicy.setOnClickListener {
            val intent = Intent(activity, AdditionalnfoActivity::class.java)
            intent.putExtra("act","pp")
            startActivity(intent)
        }
        return view
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
