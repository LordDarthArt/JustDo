package tk.lorddarthart.justdoitlist.util.helper

import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App

fun Button.setTextDisabled() {
    this.setTextColor(ContextCompat.getColor(App.instance, R.color.textDisabledColor))
}

fun Button.setTextEnabled() {
    this.setTextColor(ContextCompat.getColor(App.instance, R.color.textColor))
}

fun ImageView.clickHidePass(editText: EditText) {
    if (this.tag== R.drawable.ic_eye_visible) {
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        this.setImageResource(R.drawable.ic_eye_unvisible)
        this.tag = R.drawable.ic_eye_unvisible
    } else if (this.tag== R.drawable.ic_eye_unvisible) {
        editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        this.setImageResource(R.drawable.ic_eye_visible)
        this.tag = R.drawable.ic_eye_visible
    }
    editText.setSelection(editText.text.length)
}