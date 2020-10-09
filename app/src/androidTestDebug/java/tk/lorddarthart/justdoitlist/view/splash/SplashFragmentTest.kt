package tk.lorddarthart.justdoitlist.view.splash

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tk.lorddarthart.justdoitlist.R

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashFragmentTest {

    @Test
    fun isSplashVisible() {
        val fragmentScenario = launchFragmentInContainer<SplashFragment>()
        onView(withId(R.id.splash_logo)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}