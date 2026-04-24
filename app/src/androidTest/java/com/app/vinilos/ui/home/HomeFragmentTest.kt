package com.app.vinilos.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.vinilos.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Test
    fun homeShouldRenderAllElementsWhenOpened() {
        launchFragmentInContainer<HomeFragment>(
            themeResId = R.style.Theme_Vinilos
        )

        onView(withText("Vinilos"))
            .check(matches(isDisplayed()))

        onView(withText(R.string.home_hero_subtitle))
            .check(matches(isDisplayed()))

        onView(withText("Albums Catalogue"))
            .check(matches(isDisplayed()))

        onView(withText("Artists List"))
            .check(matches(isDisplayed()))
    }
}