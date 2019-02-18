package tk.lorddarthart.justdoitlist

import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class HidePass {

    private fun btnSetTag(view: View, resource: Int) {
        if (view is ImageView) {
            view.setImageResource(resource)
        }
        view.tag = resource
    }

    fun clickHidePass(imageView: ImageView, editText: EditText) {
        if (imageView.tag==R.drawable.ic_eye_visible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            btnSetTag(imageView, R.drawable.ic_eye_unvisible)
        } else if (imageView.tag==R.drawable.ic_eye_unvisible) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            btnSetTag(imageView, R.drawable.ic_eye_visible)
        }
        editText.setSelection(editText.text.length)
    }
}