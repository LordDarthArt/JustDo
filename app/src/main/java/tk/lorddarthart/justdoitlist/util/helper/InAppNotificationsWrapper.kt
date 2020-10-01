package tk.lorddarthart.justdoitlist.util.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

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