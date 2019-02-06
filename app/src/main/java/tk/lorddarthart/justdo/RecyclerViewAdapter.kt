package tk.lorddarthart.justdo

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.single_item_todo.view.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    internal lateinit var context: Context
    internal lateinit var Objects: ArrayList<ToDoItem>
    internal lateinit var view: View
    internal lateinit var viewHolder: ViewHolder
    internal var size: Int = 0
    private val db = FirebaseFirestore.getInstance()

    fun RecyclerViewAdapter(context: Context, Objects: ArrayList<ToDoItem> ) {
        this.context = context
        this.Objects = Objects
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.single_item_todo, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sD = SimpleDateFormat.getDateInstance()
        val objects = this.Objects[position]
        holder.clToDoDate.setOnClickListener {
            if (holder.listToDo.visibility == View.VISIBLE) {
                holder.listToDo.visibility = View.GONE
            } else if (holder.listToDo.visibility == View.GONE) {
                holder.listToDo.visibility = View.VISIBLE
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
        internal var listToDo: ListView = view.listToDo

    }
}