package tk.lorddarthart.justdoitlist.util.helper

import android.content.Context
import android.widget.ListView
import tk.lorddarthart.justdoitlist.BuildConfig
import tk.lorddarthart.justdoitlist.R

object UsefulTools {
    val isDebug: Boolean
        get() = BuildConfig.DEBUG

    fun setListViewHeightBasedOnChildren(listView: ListView, mContext: Context): Int? {
        val listAdapter = listView.adapter
                ?: // pre-condition
                return null

        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            totalHeight += mContext.resources.getDimensionPixelSize(R.dimen.listview_item_height)
        }

        val params = listView.layoutParams
        params.height = totalHeight + mContext.resources.getDimensionPixelSize(R.dimen.listview_addition)
        listView.layoutParams = params
        listView.requestLayout()
        return params.height
    }
}