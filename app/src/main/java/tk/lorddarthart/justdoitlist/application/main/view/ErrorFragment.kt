package tk.lorddarthart.justdoitlist.application.main.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_error.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ErrorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    private val TAG = javaClass.name.toString()

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
        mView = inflater.inflate(R.layout.fragment_error, container, false)

        mView.button_refresh_error.setOnClickListener {
            val fragment = LoadingFragment()
            mActivity.supportFragmentManager.beginTransaction().replace(R.id.fragment_todo, fragment).commit()
        }

        return mView
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ErrorFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
