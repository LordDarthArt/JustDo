package tk.lorddarthart.justdoitlist

import android.support.v7.app.ActionBar
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.single_item_todo.view.*
import java.text.SimpleDateFormat
import android.view.animation.DecelerateInterpolator
import android.animation.ValueAnimator


open class RecyclerViewAdapter(internal var context: Context?, internal var objects: List<ToDoItemDay>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    internal lateinit var view: View
    private lateinit var viewHolder: ViewHolder
    private var size: Int = 0
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item_todo, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        SimpleDateFormat.getDateInstance()
        val objects: ToDoItemDay = this.objects[position]
        holder.tvToDoDate.text = objects.titleDay
        holder.clToDoDate.setOnClickListener {
            if (holder.listToDo.visibility == View.VISIBLE) {
                collapse(holder.listToDo, 1000, 0)
                holder.ivToDoArrow.setImageDrawable(view.context.resources.getDrawable(R.drawable.ic_arrow_down))
            } else if (holder.listToDo.visibility == View.GONE) {
                initializeListView(holder.listToDo, objects.listToDo)
                holder.ivToDoArrow.setImageDrawable(view.context.resources.getDrawable(R.drawable.ic_arrow_up))
            }
        }
    }

    override fun getItemCount(): Int {
        return getReturnCount()
    }

    fun getReturnCount(): Int {
        return size
    }

    fun setReturnCount(size: Int) {
        this.size = size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var clToDoDate: ConstraintLayout = view.clToDoDate
        internal var tvToDoDate: TextView = view.tvToDoDate
        internal var ivToDoArrow: ImageView = view.ivToDoArrow
        internal var listToDo: ListView = view.listToDo

    }

    private fun initializeListView(list: ListView, lists: List<ToDoItem>) {
        val adapter = ListViewAdapter(context!!, R.layout.single_item_todo_listview, lists)
        adapter.notifyDataSetChanged()
        list.adapter = adapter
        expand(list, 1000, Utility.setListViewHeightBasedOnChildren(list)!!)
        list.divider=null
        list.dividerHeight=0
    }

    private fun expand(v: View, duration: Int, targetHeight: Int) {

        val prevHeight = v.height

        v.visibility = View.VISIBLE
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        //valueAnimator.doOnStart { v.visibility = View.VISIBLE }
        valueAnimator.start()
    }

    private fun collapse(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
            animation
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
        //valueAnimator.doOnEnd { v.visibility = View.GONE }
    }
}