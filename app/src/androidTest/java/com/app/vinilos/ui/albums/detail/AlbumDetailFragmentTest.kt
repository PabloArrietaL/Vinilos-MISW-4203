package com.app.vinilos.ui.albums.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.vinilos.MainActivity
import com.app.vinilos.R
import com.app.vinilos.ui.AccessibilityTestBase
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest : AccessibilityTestBase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldNavigateFromAlbumsCatalogueToAlbumDetail() {
        onView(withText(R.string.home_card_albums)).perform(click())

        onView(withId(R.id.rvAlbums))
            .perform(waitForRecyclerViewItems(10_000))

        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.scrollContent))
            .check(matches(isDisplayed()))
    }

    @Test
    fun albumDetailShouldDisplayNameAndPerformers() {
        onView(withText(R.string.home_card_albums)).perform(click())

        onView(withId(R.id.rvAlbums))
            .perform(waitForRecyclerViewItems(10_000))

        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvName))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tvPerformers))
            .check(matches(isDisplayed()))
    }

    @Test
    fun albumDetailShouldDisplayMetadata() {
        onView(withText(R.string.home_card_albums)).perform(click())

        onView(withId(R.id.rvAlbums))
            .perform(waitForRecyclerViewItems(10_000))

        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvReleaseDate))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tvGenre))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tvRecordLabel))
            .check(matches(isDisplayed()))
    }

    @Test
    fun albumDetailShouldDisplayDescription() {
        onView(withText(R.string.home_card_albums)).perform(click())

        onView(withId(R.id.rvAlbums))
            .perform(waitForRecyclerViewItems(10_000))

        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvDescription))
            .check(matches(isDisplayed()))
    }

    @Test
    fun albumDetailShouldDisplayTracksList() {
        onView(withText(R.string.home_card_albums)).perform(click())

        onView(withId(R.id.rvAlbums))
            .perform(waitForRecyclerViewItems(10_000))

        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.rvTracks))
            .check(matches(isDisplayed()))
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
}