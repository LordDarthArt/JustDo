package tk.lorddarthart.justdoitlist

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class SlideAnimation(private var mView: View, private var mFromHeight: Int, private var mToHeight: Int) : Animation() {

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val newHeight: Int

        if (mView.height !== mToHeight) {
            newHeight = (mFromHeight + (mToHeight - mFromHeight) * interpolatedTime).toInt()
            mView.layoutParams.height = newHeight
            mView.requestLayout()
        }
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}