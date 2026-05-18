package com.app.vinilos.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.platform.app.InstrumentationRegistry
import com.app.vinilos.MainActivity
import com.app.vinilos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExploratoryTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val device = UiDevice.getInstance(
        InstrumentationRegistry.getInstrumentation()
    )

    /**
     * Exploración sistemática: recorre todos los flujos navegables de la app
     * en orden lógico, verificando que no hay crashes en ninguna transición.
     */
    @Test
    fun systematicExploration_allScreensReachableWithoutCrash() {
        // 1. Home → Albums Catalogue
        onView(withText(R.string.home_card_albums)).perform(click())
        device.waitForIdle(3_000)

        // 2. Albums Catalogue → Album Detail (primer ítem)
        androidx.test.espresso.contrib.RecyclerViewActions
            .actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(
                0, click()
            ).let {
                onView(androidx.test.espresso.matcher.ViewMatchers.withId(R.id.rvAlbums))
                    .perform(it)
            }
        device.waitForIdle(3_000)

        // 3. Volver a Albums Catalogue
        device.pressBack()
        device.waitForIdle(1_000)

        // 4. Volver a Home
        device.pressBack()
        device.waitForIdle(1_000)

        // 5. Home → Artists List
        onView(withText(R.string.home_card_artists)).perform(click())
        device.waitForIdle(3_000)

        // 6. Artists List → Artist Detail (primer ítem)
        androidx.test.espresso.contrib.RecyclerViewActions
            .actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(
                0, click()
            ).let {
                onView(androidx.test.espresso.matcher.ViewMatchers.withId(R.id.rvArtists))
                    .perform(it)
            }
        device.waitForIdle(3_000)

        // 7. Volver a Artists List
        device.pressBack()
        device.waitForIdle(1_000)

        // 8. Volver a Home
        device.pressBack()
        device.waitForIdle(1_000)

        // 9. Rotar pantalla en Home y verificar estabilidad
        device.setOrientationLeft()
        device.waitForIdle(2_000)
        device.setOrientationNatural()
        device.waitForIdle(2_000)
    }

    /**
     * Exploración aleatoria controlada: navega a cada sección
     * e introduce eventos de sistema (back, rotate) en puntos intermedios.
     */
    @Test
    fun randomExploration_systemEventsDoNotCrashApp() {
        // Albums Catalogue con rotación
        onView(withText(R.string.home_card_albums)).perform(click())
        device.waitForIdle(2_000)
        device.setOrientationLeft()
        device.waitForIdle(2_000)
        device.setOrientationNatural()
        device.waitForIdle(1_000)
        device.pressBack()
        device.waitForIdle(1_000)

        // Artists List con rotación
        onView(withText(R.string.home_card_artists)).perform(click())
        device.waitForIdle(2_000)
        device.setOrientationLeft()
        device.waitForIdle(2_000)
        device.setOrientationNatural()
        device.waitForIdle(1_000)
        device.pressBack()
        device.waitForIdle(1_000)
    }
}