package tk.lorddarthart.justdoitlist.view.main.home.todo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.model.pojo.main.ToDoItemModel
import tk.lorddarthart.justdoitlist.databinding.SingleItemTodoListviewBinding
import tk.lorddarthart.justdoitlist.util.converters.PriorityConverterImpl
import java.text.SimpleDateFormat
import java.util.*

class ListViewAdapter(
    private val actualContext: Context,
    private val resource: Int,
    private val toDoItemModels: List<ToDoItemModel>
) : ArrayAdapter<ToDoItemModel>(actualContext, resource, toDoItemModels) {

    // items count
    override fun getCount(): Int {
        return toDoItemModels.size
    }

    // element by position
    override fun getItem(position: Int): ToDoItemModel? {
        return toDoItemModels[position]
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
        val viewBinding = SingleItemTodoListviewBinding.inflate(LayoutInflater.from(actualContext), parent, false)
        val toDoItem = getToDoItem(position)
        val priorityConverter = PriorityConverterImpl()
        priorityConverter.init(actualContext)

        with (viewBinding) {
            setTitle(toDoItem.title)
            setComment(toDoItem.comment)
            setTimeText(toDoItem.timestamp.toString())
            setIcon(toDoItem, priorityConverter)
            toDoItem.completed?.let {
                chkToDo.isChecked = it
            }
        }

        return viewBinding.root
    }

    private fun SingleItemTodoListviewBinding.setIcon(toDoItem: ToDoItemModel, priorityConverter: PriorityConverterImpl) {
        val icon: Drawable = ContextCompat.getDrawable(root.context, R.drawable.shape_priority_circle)!!
        priorityConverter.getColor(toDoItem.priority).let {
            icon.setTint(it)
        }
        ivPriorityMarker.setImageDrawable(icon)
    }

    @SuppressLint("SimpleDateFormat")
    private fun SingleItemTodoListviewBinding.setTimeText(time: String) {
        tvToDoTime.text = SimpleDateFormat("HH:mm").format(Date(time.toLong()))
    }

    private fun SingleItemTodoListviewBinding.setComment(comment: String?) {
        tvToDoComment.text = comment
    }

    private fun SingleItemTodoListviewBinding.setTitle(title: String?) {
        tvToDoTitle.text = title
    }

}