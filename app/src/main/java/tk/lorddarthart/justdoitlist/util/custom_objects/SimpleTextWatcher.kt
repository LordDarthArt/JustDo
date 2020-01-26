package tk.lorddarthart.justdoitlist.util.custom_objects

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(private val action: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        // do something
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // do something
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        action(s, start, before, count)
    }
}