package tk.lorddarthart.justdoitlist.view.main.home.todo.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.single_item_todo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemDayModel
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemModel
import tk.lorddarthart.justdoitlist.util.constants.TimeConstant.ONE_HUNDRED_MILLIS
import tk.lorddarthart.justdoitlist.util.constants.TimeConstant.ONE_SECOND
import tk.lorddarthart.justdoitlist.util.constants.Utility
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class ToDoViewAdapter(
    private var context: Context?,
    private var toDoItems: List<ToDoItemDayModel>
) : RecyclerView.Adapter<ToDoViewAdapter.ViewHolder>() {
    private lateinit var view: View
    private lateinit var viewHolder: ViewHolder
    private var size: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item_todo, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        SimpleDateFormat.getDateInstance()
        val objects: ToDoItemDayModel = this.toDoItems[position]
        holder.toDoDateText.text = objects.titleDay
        holder.toDoDate.setOnClickListener { toDoDate ->
            if (holder.listToDo.visibility == View.VISIBLE) {
                collapse(holder.listToDo, ONE_SECOND.toInt(), 0)
                holder.toDoArrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.arrow_down))
            } else if (holder.listToDo.visibility == View.GONE) {
                initializeListView(holder.listToDo, objects.listToDoModel!!)
                holder.toDoArrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.arrow_up))
            }
            toDoDate.isEnabled = false
            Single.create<() -> Unit> { toDoDate.isEnabled = true }.subscribeOn(Schedulers.newThread())
                .delay(1, TimeUnit.SECONDS)
                .subscribe { executable ->
                    executable()
                }
        }
    }

    override fun getItemCount(): Int {
        return getReturnCount()
    }

    private fun getReturnCount(): Int {
        return size
    }

    fun setReturnCount(size: Int) {
        this.size = size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var toDoDate: ConstraintLayout = view.clToDoDate
        internal var toDoDateText: TextView = view.tvToDoDate
        internal var toDoArrow: ImageView = view.ivToDoArrow
        internal var listToDo: ListView = view.listToDo
    }

    private fun initializeListView(list: ListView, lists: List<ToDoItemModel>) {
        context?.let { context ->
            val adapter = ListViewAdapter(context, R.layout.single_item_todo_listview, lists)
            adapter.notifyDataSetChanged()
            list.adapter = adapter
            val height = Utility.setListViewHeightBasedOnChildren(list, context)
            height?.let { heightValue ->
                expand(list, heightValue)
            }
            list.divider = null
            list.dividerHeight = 0
        }
    }

    private fun expand(v: View, targetHeight: Int) {

        val prevHeight = v.height

        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = ONE_SECOND
        val listener = object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                Single.create<() -> Unit> { v.visibility = View.VISIBLE  }
                    .subscribeOn(Schedulers.single())
                    .delay(ONE_HUNDRED_MILLIS, TimeUnit.MILLISECONDS)
                    .subscribe { executable ->
                        executable()
                    }
            }
        }
        valueAnimator.addListener(listener)
        valueAnimator.start()
    }

    private fun collapse(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
            if (v.layoutParams.height <= 0) {
                CoroutineScope(Dispatchers.Main).launch {
                    v.visibility = View.GONE
                }
            }
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        val listener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                v.visibility = View.GONE
            }
        }
        valueAnimator.addListener(listener)
        valueAnimator.start()
    }
}