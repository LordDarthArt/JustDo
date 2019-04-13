package tk.lorddarthart.justdoitlist.utils

import android.view.View.MeasureSpec
import android.widget.ListView

object Utility {
    fun setListViewHeightBasedOnChildren(listView: ListView): Int? {
        val listAdapter = listView.adapter
                ?: // pre-condition
                return null

        var totalHeight = 0
        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.AT_MOST)
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listView.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += listView.measuredHeight/listAdapter.count
        }

        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
        listView.requestLayout()
        return params.height
    }
}