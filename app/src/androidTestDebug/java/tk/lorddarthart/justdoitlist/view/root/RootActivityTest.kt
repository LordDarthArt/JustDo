package tk.lorddarthart.justdoitlist.view.root

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tk.lorddarthart.justdoitlist.R

@RunWith(AndroidJUnit4ClassRunner::class)
class RootActivityTest {
    @get: Rule
    val activityScenario = ActivityScenarioRule(RootActivity::class.java)

    @Test
    fun testDisplayed() {
        onView(withId(R.id.base_container)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}