package com.app.vinilos.ui.albums.catalogue

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.vinilos.MainActivity
import com.app.vinilos.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsCatalogueFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldNavigateFromHomeToAlbumsCatalogue() {
        onView(withText(R.string.home_card_albums)).perform(
            androidx.test.espresso.action.ViewActions.click()
        )

        onView(withId(R.id.rvAlbums))
            .check(matches(isDisplayed()))


        onView(withText("Albums"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun catalogueShouldDisplayAlbumsWhenLoaded() {

        onView(withText(R.string.home_card_albums)).perform(
            androidx.test.espresso.action.ViewActions.click()
        )

        onView(withId(R.id.rvAlbums))
            .perform(waitForRecyclerViewItems(10_000))

        onView(withId(R.id.rvAlbums))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumItemCount(1)))
    }

    private fun waitForRecyclerViewItems(timeoutMillis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> =
                isAssignableFrom(RecyclerView::class.java)

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
        return object : org.hamcrest.TypeSafeMatcher<View>() {
            override fun describeTo(description: org.hamcrest.Description) {
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