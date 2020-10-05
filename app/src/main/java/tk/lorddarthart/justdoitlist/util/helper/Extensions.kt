package tk.lorddarthart.justdoitlist.util.helper

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import tk.lorddarthart.justdoitlist.BuildConfig
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.view.base.BaseFragment

interface Loggable

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

inline fun View.shortSnackbar(text: () -> String) {
    Snackbar.make(this, text.invoke(), Snackbar.LENGTH_SHORT).show()
}

inline fun View.longSnackbar(text: () -> String) {
    Snackbar.make(this, text.invoke(), Snackbar.LENGTH_LONG).show()
}

inline fun Context.shortToast(text: () -> String) {
    Toast.makeText(this, text.invoke(), Toast.LENGTH_SHORT).show()
}

inline fun Context.longToast(text: () -> String) {
    Toast.makeText(this, text.invoke(), Toast.LENGTH_LONG).show()
}

/** Lazy wrapper over [Log.d] */
inline fun <reified T: Loggable> T.logDebug( lazyMessage: () -> String) {
    if (isDebug) {
        Log.d(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.v] */
inline fun <reified T : Loggable> T.logVerbose(lazyMessage: () -> String) {
    if (isDebug) {
        Log.v(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.i]. */
inline fun <reified T : Loggable> T.logInfo(lazyMessage: () -> String) {
    if (isDebug) {
        Log.i(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.w]. */
inline fun <reified T : Loggable> T.logWarning(lazyMessage: () -> String) {
    if (isDebug) {
        Log.w(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.e] with [Exception]. */
inline fun <reified T : Loggable> T.logError(exception: Exception? = null, lazyMessage: () -> String) {
    if (isDebug) {
        Log.e(
            classSimpleName,
            lazyMessage.invoke(),
            exception
        )
    }
}

/** Lazy wrapper over [Log.e] w/o [Exception]. */
inline fun <reified T : Loggable> T.logError(lazyMessage: () -> String) {
    if (isDebug) {
        Log.e(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Variable that contains the name of the class from within it is invoked. */
inline val <reified T : Loggable> T.classSimpleName: String
    get() = if (T::class.java.simpleName.isNotBlank()) { T::class.java.simpleName } else { "Anonymous" }

val isDebug: Boolean
    get() = BuildConfig.DEBUG