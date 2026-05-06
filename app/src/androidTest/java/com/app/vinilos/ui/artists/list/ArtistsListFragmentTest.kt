package com.app.vinilos.ui.artists.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.vinilos.MainActivity
import com.app.vinilos.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistsListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldNavigateFromHomeToArtistsList() {
        Espresso.onView(ViewMatchers.withText(R.string.home_card_artists))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.rvArtists))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Artists"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun artistsListShouldDisplayArtistsWhenLoaded() {
        Espresso.onView(ViewMatchers.withText(R.string.home_card_artists))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.rvArtists))
            .perform(waitForRecyclerViewItems(10_000))

        Espresso.onView(ViewMatchers.withId(R.id.rvArtists))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(hasMinimumItemCount(1)))
    }

    private fun waitForRecyclerViewItems(timeoutMillis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> =
                ViewMatchers.isAssignableFrom(RecyclerView::class.java)

            override fun getDescription(): String =
                "Esperar a que el RecyclerView tenga al menos 1 ítem"

            override fun perform(uiController: UiController, view: View) {
                val recycler = view as RecyclerView
                val endTime = System.currentTimeMillis() + timeoutMillis

                while (System.currentTimeMillis() < endTime) {
                    val itemCount = recycler.adapter?.itemCount ?: 0
                    if (itemCount > 0) return
                    uiController.loopMainThreadForAtLeast(100)
                }

                throw AssertionError(
                    "El RecyclerView no se llenó en $timeoutMillis ms. " +
                            "¿BackVynils está corriendo en Docker?"
                )
            }
        }
    }

    private fun hasMinimumItemCount(min: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("RecyclerView con al menos $min ítems")
            }

            override fun matchesSafely(view: View): Boolean {
                val recycler = view as? RecyclerView ?: return false
                val count = recycler.adapter?.itemCount ?: 0
                return count >= min
            }
        }
    }
}