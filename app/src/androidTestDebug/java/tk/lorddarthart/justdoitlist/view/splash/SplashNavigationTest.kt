package tk.lorddarthart.justdoitlist.view.splash

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import tk.lorddarthart.justdoitlist.R
import tk.lorddarthart.justdoitlist.view.root.RootActivity

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashNavigationTest {
    @Test
    fun isMovingToAuth() {
        ActivityScenario.launch(RootActivity::class.java)
        FirebaseAuth.getInstance().signOut()
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.auth_root)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun isMovingToHomeWithNotVerifiedAccount() {
        ActivityScenario.launch(RootActivity::class.java)
        FirebaseAuth.getInstance().signInWithEmailAndPassword("test2@lorddarthart.tk", "QWerty1234!")
        onView(isRoot()).perform(waitFor(5000))
        onView(withId(R.id.home_root)).check(doesNotExist())
    }

    @Test
    fun isMovingToHomeWithVerifiedAccount() {
        ActivityScenario.launch(RootActivity::class.java)
        FirebaseAuth.getInstance().signInWithEmailAndPassword("test@lorddarthart.tk", "QWerty1234!")
        onView(isRoot()).perform(waitFor(5000))
        onView(withId(R.id.home_root)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }

            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }
        }
    }
}