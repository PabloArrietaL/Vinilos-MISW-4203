package com.app.vinilos.ui

import androidx.test.espresso.accessibility.AccessibilityChecks
import org.junit.BeforeClass

abstract class AccessibilityTestBase {

    companion object {
        @BeforeClass
        @JvmStatic
        fun enableAccessibilityChecks() {
            AccessibilityChecks.enable()
                .setRunChecksFromRootView(true)
        }
    }
}