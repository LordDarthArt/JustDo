package tk.lorddarthart.justdoitlist.application.main.todo.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.single_item_todo_listview.view.*
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.application.main.todo.model.ToDoItemModel
import tk.lorddarthart.justdoitlist.utils.converters.PriorityConverter
import java.text.SimpleDateFormat
import java.util.*

class ListViewAdapter(
        internal var context: Context,
        resource: Int,
        objects: List<ToDoItemModel>
) : ArrayAdapter<ToDoItemModel>(context, resource, objects) {
    private var mObjects: List<ToDoItemModel> = objects

    // items count
    override fun getCount(): Int {
        return mObjects.size
    }

    // element by position
    override fun getItem(position: Int): ToDoItemModel? {
        return mObjects[position]
    }

    // item id by position
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // ToDoItem by position
    private fun getToDoItem(position: Int): ToDoItemModel {
        return getItem(position) as ToDoItemModel
    }

    @SuppressLint("SimpleDateFormat", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item_todo_listview, parent, false)
        val toDoItem = getToDoItem(position)

        view.tvToDoTitle?.text = toDoItem.title
        view.tvToDoComment?.text = toDoItem.comment
        view.tvToDoTime?.text = SimpleDateFormat("HH:mm").format(Date(toDoItem.timestamp!!))
        val icon: Drawable = context.getDrawable(R.drawable.shape_priority_circle)!!
        icon.setTint(PriorityConverter.getColor(PriorityConverter.getPriority(toDoItem.priority, view), view)!!)
        view.ivPriorityMarker.setImageDrawable(icon)
        toDoItem.completed?.let {
            view.chkToDo.isChecked = it
        } ?: apply {
            view.chkToDo.isChecked = false
        }

        return view
    }
}