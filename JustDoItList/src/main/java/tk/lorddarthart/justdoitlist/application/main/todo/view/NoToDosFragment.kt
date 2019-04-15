package tk.lorddarthart.justdoitlist.application.main.todo.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NoToDosFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

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
        mView = inflater.inflate(R.layout.fragment_no_todos, container, false)

        // TODO: do something

        return mView
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NoToDosFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
