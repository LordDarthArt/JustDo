package tk.lorddarthart.justdoitlist

import android.app.ActionBar
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.single_item_todo.view.*
import java.text.SimpleDateFormat



class RecyclerViewAdapter(internal var context: Context?, internal var objects: List<ToDoItemDay>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    internal lateinit var view: View
    private lateinit var viewHolder: ViewHolder
    private var size: Int = 0
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item_todo, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        SimpleDateFormat.getDateInstance()
        val objects: ToDoItemDay = this.objects[position]
        holder.tvToDoDate.text = objects.titleDay
        holder.clToDoDate.setOnClickListener {
            if (holder.listToDo.visibility == View.VISIBLE) {
                holder.listToDo.visibility = View.GONE
                holder.ivToDoArrow.setImageDrawable(view.context.resources.getDrawable(R.drawable.ic_arrow_down))
            } else if (holder.listToDo.visibility == View.GONE) {
                holder.listToDo.visibility = View.VISIBLE
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

    fun initializeListView(list: ListView, lists: List<ToDoItem>) {
        val adapter = ListViewAdapter(context!!, R.layout.single_item_todo_listview, lists)
        adapter.notifyDataSetChanged()
        list.adapter = adapter
        Utility.setListViewHeightBasedOnChildren(list)
        view.layoutParams=RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        list.divider==null
        list.dividerHeight==0
    }
}