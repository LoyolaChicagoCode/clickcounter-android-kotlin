package edu.luc.etl.cs313.android.clickcounter

import android.content.pm.ActivityInfo
import android.widget.Button
import android.widget.TextView

import org.junit.Ignore
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

/**
 * Abstract GUI-level test superclass for several essential click-counter
 * scenarios.

 * @author laufer
 * *
 *
 *
 * *         TODO figure out how to move this from main to test in Gradle
 */
@Ignore
abstract class AbstractClickCounterActivityTest {

    /** Verifies that the activity under test can be launched.  */
    @Test
    fun testActivityTestCaseSetUpProperly() {
        assertNotNull("activity should be launched successfully", activity)
    }

    // begin-method-testActivityScenarioIncReset
    @Test
    fun testActivityScenarioIncReset() {
        assertTrue(resetButton.performClick())
        assertEquals(0, displayedValue.toLong())
        assertTrue(incButton.isEnabled)
        assertFalse(decButton.isEnabled)
        assertTrue(resetButton.isEnabled)
        assertTrue(incButton.performClick())
        assertEquals(1, displayedValue.toLong())
        assertTrue(incButton.isEnabled)
        assertTrue(decButton.isEnabled)
        assertTrue(resetButton.isEnabled)
        assertTrue(resetButton.performClick())
        assertEquals(0, displayedValue.toLong())
        assertTrue(incButton.isEnabled)
        assertFalse(decButton.isEnabled)
        assertTrue(resetButton.isEnabled)
        assertTrue(resetButton.performClick())
    }
    // end-method-testActivityScenarioIncReset

    @Test
    fun testActivityScenarioIncUntilFull() {
        assertTrue(resetButton.performClick())
        assertEquals(0, displayedValue.toLong())
        assertTrue(incButton.isEnabled)
        assertFalse(decButton.isEnabled)
        assertTrue(resetButton.isEnabled)
        while (incButton.isEnabled) {
            val v = displayedValue
            assertTrue(incButton.performClick())
            assertEquals((v + 1).toLong(), displayedValue.toLong())
        }
        assertFalse(incButton.isEnabled)
        assertTrue(decButton.isEnabled)
        assertTrue(resetButton.isEnabled)
        assertTrue(resetButton.performClick())
    }

    // begin-method-testActivityScenarioRotation
    @Test
    fun testActivityScenarioRotation() {
        assertTrue(resetButton.performClick())
        assertEquals(0, displayedValue.toLong())
        assertTrue(incButton.performClick())
        assertTrue(incButton.performClick())
        assertTrue(incButton.performClick())
        assertEquals(3, displayedValue.toLong())
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        assertEquals(3, displayedValue.toLong())
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        assertEquals(3, displayedValue.toLong())
        assertTrue(resetButton.performClick())
    }
    // end-method-testActivityScenarioRotation

    // auxiliary methods for easy access to UI widgets

    protected abstract val activity: ClickCounterActivity

    protected val displayedValue: Int
        get() {
            val t = activity.findViewById(R.id.textview_value) as TextView
            return Integer.parseInt(t.text.toString().trim { it <= ' ' })
        }

    protected val incButton: Button
        get() = activity.findViewById(R.id.button_increment) as Button

    protected val decButton: Button
        get() = activity.findViewById(R.id.button_decrement) as Button

    protected val resetButton: Button
        get() = activity.findViewById(R.id.button_reset) as Button
}
