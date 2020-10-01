package tk.lorddarthart.justdoitlist.util.helper

import android.util.Log
import tk.lorddarthart.justdoitlist.util.helper.UsefulTools.isDebug

interface Loggable

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

/** Lazy wrapper over [Log.i] */
inline fun <reified T : Loggable> T.logInfo(lazyMessage: () -> String) {
     if (isDebug) {
        Log.i(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.w] */
inline fun <reified T : Loggable> T.logWarning(lazyMessage: () -> String) {
     if (isDebug) {
        Log.w(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/** Lazy wrapper over [Log.e] with [Exception]*/
inline fun <reified T : Loggable> T.logError(exception: Exception? = null, lazyMessage: () -> String) {
     if (isDebug) {
        Log.e(
            classSimpleName,
            lazyMessage.invoke(),
            exception
        )
    }
}

/** Lazy wrapper over [Log.e] w/o [Exception] */
inline fun <reified T : Loggable> T.logError(lazyMessage: () -> String) {
     if (isDebug) {
        Log.e(
            classSimpleName,
            lazyMessage.invoke()
        )
    }
}

/**
 * Variable that contains the name of the class from within it is invoked.
 */
inline val <reified T : Loggable> T.classSimpleName: String
    get() = if (T::class.java.simpleName.isNotBlank()) { T::class.java.simpleName } else { "Anonymous" }