package tk.lorddarthart.justdo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

@SuppressLint("StaticFieldLeak")

class ListViewAdapter: ArrayAdapter<ToDoItem> {
    private var resource: Int = 0
    private var objects: Array<ToDoItem>

    constructor(context: Context, resource: Int, objects: Array<ToDoItem>) : super(context, resource) {
        this.resource = resource
        this.objects = objects
    }


    override fun getView (position: Int, convertView: View, parent: ViewGroup): View {
        val priority = getItem(position)?.priority
        val title = getItem(position)?.title
        val comment = getItem(position)?.comment
        val timestamp = getItem(position)?.timestamp


        var toDoItem = ToDoItem(priority, title, comment, timestamp)

        var inflater = LayoutInflater.from(context)
        var convertView = inflater.inflate(resource, parent, false)

        //TextView tvToDOTitle
        return convertView
    }
}