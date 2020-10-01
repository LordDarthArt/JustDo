package tk.lorddarthart.justdoitlist.util.helper

import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.presentation.base.BaseFragment

inline fun <reified T : Button> T.setTextDisabled() {
    setTextColor(ContextCompat.getColor(context, R.color.textDisabledColor))
}

inline fun <reified T : Button> T.setTextEnabled() {
    setTextColor(ContextCompat.getColor(context, R.color.textColor))
}

inline fun <reified T : View> T.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

inline fun <reified T : View> T.setGone() {
    visibility = View.GONE
}

inline fun <reified T : View> T.setInvisible() {
    visibility = View.INVISIBLE
}

inline fun <reified T : View> T.setVisible() {
    visibility = View.VISIBLE
}

inline val <reified T : BaseFragment> T.fragmentTag: String
    get() = T::class.java.simpleName