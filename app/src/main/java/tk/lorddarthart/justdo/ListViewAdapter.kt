package tk.lorddarthart.justdo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.single_item_todo_listview.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListViewAdapter : ArrayAdapter<ToDoItem> {
    private var objects: List<ToDoItem>
    private var resource: Int
    internal var context: Context

    constructor(context: Context, resource: Int, objects: List<ToDoItem>) : super(context, resource, objects) {
        this.context = context
        this.resource = resource
        this.objects = objects
    }

    // кол-во элементов
    override fun getCount(): Int {
        return objects.size
    }

    // элемент по позиции
    override fun getItem(position: Int): ToDoItem? {
        return objects[position]
    }

    // id по позиции
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // товар по позиции
    private fun getToDoItem(position: Int): ToDoItem {
        return getItem(position) as ToDoItem
    }

    @SuppressLint("SimpleDateFormat", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item_todo_listview, parent, false)
        val toDoItem = getToDoItem(position)

        view.tvToDoTitle?.text = toDoItem.title
        view.tvToDoComment?.text = toDoItem.comment
        view.tvToDoTime?.text = SimpleDateFormat("HH:mm").format(Date(toDoItem.timestamp!!))
        val icon: Drawable = context.getDrawable(R.drawable.shape_circle)!!
        icon.setTint(PriorityConverter.getColor(PriorityConverter.getPriority(toDoItem.priority), view)!!)
        view.ivPriorityMarker.setImageDrawable(icon)
        if (toDoItem.completed!!) {
            view.chkToDo.isChecked = true
        }

        return view
    }
}