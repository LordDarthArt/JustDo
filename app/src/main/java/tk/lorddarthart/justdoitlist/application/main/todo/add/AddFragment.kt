package tk.lorddarthart.justdoitlist.application.main.todo.add


import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_add.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.BaseActivity
import tk.lorddarthart.justdoitlist.utils.converters.PriorityConverter
import tk.lorddarthart.justdoitlist.utils.listeners.BaseBackPressedListener

class AddFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var mActivity: BaseActivity

    private val TAG = javaClass.name.toString()

    private var mPriority: Long = 0L

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mActivity.setOnBackPressedListener(BaseBackPressedListener(mActivity))
        mView = inflater.inflate(R.layout.fragment_add, container, false)

        mActivity.setActionBarTitle(getString(R.string.todo_add_bar_title))

        PriorityConverter.setPriority(mActivity.resources.getString(R.string.priority_neutral), mView)?.let {
            mPriority = it
            Log.d(TAG, "Priority was set to neutral")
        }

        with(mView) {
            cl_cardview_urgently_new_todo.setOnClickListener {
                onTick(it as ConstraintLayout, mView.tvNewPriorityUrgently)
                Log.d(TAG, "Priority was set to urgently")
            }
            cl_cardview_important_new_todo.setOnClickListener {
                onTick(it as ConstraintLayout, mView.tvNewPriorityImportant)
                Log.d(TAG, "Priority was set to important")
            }
            cl_cardview_normal_new_todo .setOnClickListener {
                onTick(it as ConstraintLayout, mView.tvNewPriorityNormal)
                Log.d(TAG, "Priority was set to normal")
            }
            edittext_description_new_todo.setOnTouchListener { view, event ->
                view.parent.requestDisallowInterceptTouchEvent(true)
                if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    view.parent.requestDisallowInterceptTouchEvent(false)
                }
                return@setOnTouchListener false
            }
        }

        return mView
    }

    override fun onDestroyView() {
        mActivity.setOnBackPressedListener(null)
        super.onDestroyView()
    }

    private fun onTick(cl: ConstraintLayout, tv: TextView) {
        if (PriorityConverter.setPriority(tv.text.toString(), mView)!=mPriority) {
            when {
                cl.id == mView.cl_cardview_urgently_new_todo.id -> {
                    with(mView) {
                        ivTickNewPriorityUrgently.visibility = View.VISIBLE
                        ivTickNewPriorityImportant.visibility = View.GONE
                        ivTickNewPriorityNormal.visibility = View.GONE
                    }
                }
                cl.id == mView.cl_cardview_important_new_todo.id -> {
                    with(mView) {
                        ivTickNewPriorityUrgently.visibility = View.GONE
                        ivTickNewPriorityImportant.visibility = View.VISIBLE
                        ivTickNewPriorityNormal.visibility = View.GONE
                    }
                }
                cl.id == mView.cl_cardview_normal_new_todo.id -> {
                    with(mView) {
                        ivTickNewPriorityUrgently.visibility = View.GONE
                        ivTickNewPriorityImportant.visibility = View.GONE
                        ivTickNewPriorityNormal.visibility = View.VISIBLE
                    }
                }
            }
            PriorityConverter.setPriority(tv.text.toString(), mView)?.let {
                mPriority = it
            }
        } else {
            with(mView) {
                ivTickNewPriorityUrgently.visibility = View.GONE
                ivTickNewPriorityImportant.visibility = View.GONE
                ivTickNewPriorityNormal.visibility = View.GONE
            }
            PriorityConverter.setPriority(mView.context.resources.getString(R.string.priority_neutral), mView)?.let {
                mPriority = it
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = AddFragment()
    }
}
