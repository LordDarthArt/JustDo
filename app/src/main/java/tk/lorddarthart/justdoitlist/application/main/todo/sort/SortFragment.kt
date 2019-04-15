package tk.lorddarthart.justdoitlist.application.main.todo.sort

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.signin.signup.SignUpFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SortFragment : Fragment() {
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
        mView = inflater.inflate(R.layout.fragment_sort, container, false)

        // TODO: do something

        return mView
    }

    companion object {

        private const val TAG = "SortFragment"

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
