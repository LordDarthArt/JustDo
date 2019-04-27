package tk.lorddarthart.justdoitlist.application.main.todo.add


import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_add.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.utils.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.utils.listeners.BaseBackPressedListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    private var mPriority: Long = 0L

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
        mActivity.setOnBackPressedListener(BaseBackPressedListener(mActivity))
        mView = inflater.inflate(R.layout.fragment_add, container, false)

        //mPriority = PriorityConverter.setPriority(mActivity.resources.getString(R.string.priority_neutral), mView)

        mView.cl_cardview_urgently_new_todo.setOnClickListener {
            onTick(it as ConstraintLayout, mView.tvNewPriorityUrgently)
        }

        mView.cl_cardview_important_new_todo.setOnClickListener {
            onTick(it as ConstraintLayout, mView.tvNewPriorityImportant)
        }

        mView.cl_cardview_normal_new_todo .setOnClickListener {
            onTick(it as ConstraintLayout, mView.tvNewPriorityNormal)
        }

        return mView
    }

    override fun onDestroyView() {
        mActivity.setOnBackPressedListener(null)
        super.onDestroyView()
    }

    fun onTick(cl: ConstraintLayout, tv: TextView) {
        if (PriorityConverter.setPriority(tv.text.toString(), mView)!=mPriority) {
            if (cl.id == mView.cl_cardview_urgently_new_todo.id) {
                mView.ivTickNewPriorityUrgently.visibility = View.VISIBLE
                mView.ivTickNewPriorityImportant.visibility = View.GONE
                mView.ivTickNewPriorityNormal.visibility = View.GONE
            } else if (cl.id == mView.cl_cardview_important_new_todo.id) {
                mView.ivTickNewPriorityUrgently.visibility = View.GONE
                mView.ivTickNewPriorityImportant.visibility = View.VISIBLE
                mView.ivTickNewPriorityNormal.visibility = View.GONE
            } else if (cl.id == mView.cl_cardview_normal_new_todo.id) {
                mView.ivTickNewPriorityUrgently.visibility = View.GONE
                mView.ivTickNewPriorityImportant.visibility = View.GONE
                mView.ivTickNewPriorityNormal.visibility = View.VISIBLE
            }
            mPriority = PriorityConverter.setPriority(tv.text.toString(), mView)!!
        } else {
            mView.ivTickNewPriorityUrgently.visibility = View.GONE
            mView.ivTickNewPriorityImportant.visibility = View.GONE
            mView.ivTickNewPriorityNormal.visibility = View.GONE
            mPriority = PriorityConverter.setPriority(mView.context.resources.getString(R.string.priority_neutral), mView)!!
        }
    }

    companion object {

        private const val TAG = "AddFragment"

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
