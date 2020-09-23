package tk.lorddarthart.justdoitlist.util.custom_objects

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.app.App
import tk.lorddarthart.justdoitlist.app.model.pojo.auth.Link
import tk.lorddarthart.justdoitlist.app.view.fragment.auth.additional_info.AdditionalInfoFragment
import tk.lorddarthart.justdoitlist.util.navigation.NavUtils
import tk.lorddarthart.smartnavigation.types.NavigationActionType
import tk.lorddarthart.smartnavigation.types.NavigationAnimType
import java.util.regex.Pattern
import javax.inject.Inject

class CustomSpannableString(
        spannableText: String,
        private val links: List<Link>,
        private val spannableHolderView: TextView
): SpannableString(spannableText) {
    @Inject lateinit var navUtils: NavUtils

    fun createForAuthScreen() {
        for (link in links) {
            val linkPattern = Pattern.compile(link.needToHighlightString)
            val linkMatcher = linkPattern.matcher(this)

            val linkClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navUtils.baseNavigator?.navigate(AdditionalInfoFragment(), NavigationActionType.AddToBackStackAction, NavigationAnimType.FadeAnim, link.fragmentBundle)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = ContextCompat.getColor(App.INSTANCE, R.color.textColor)
                }
            }

            while (linkMatcher.find()) {
                setSpan(linkClickableSpan, linkMatcher.start(), linkMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        spannableHolderView.movementMethod = LinkMovementMethod.getInstance()
    }
}