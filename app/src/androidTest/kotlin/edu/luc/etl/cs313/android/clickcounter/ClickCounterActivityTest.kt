package edu.luc.etl.cs313.android.clickcounter

import android.test.ActivityInstrumentationTestCase2
import android.test.UiThreadTest

/**
 * Concrete Android test subclass. Has to inherit from framework class
 * and uses delegation to concrete subclass of abstract test superclass.
 * IMPORTANT: project must export JUnit 4 to make it available on the
 * device.

 * @author laufer
 * *
 * @see http://developer.android.com/tools/testing/activity_testing.html
 */
class ClickCounterActivityTest : ActivityInstrumentationTestCase2<ClickCounterActivity>(ClickCounterActivity::class.java) {

    init {
        actualTest = object : AbstractClickCounterActivityTest() {
            override // return activity instance provided by instrumentation test
            val activity: ClickCounterActivity
                get() = this@ClickCounterActivityTest.activity
        }
    }

    // test subclass instance to delegate to
    private val actualTest: AbstractClickCounterActivityTest

    fun testActivityTestCaseSetUpProperly() {
        actualTest.testActivityTestCaseSetUpProperly()
    }

    // begin-method-testActivityScenarioIncReset
    @UiThreadTest
    fun testActivityScenarioIncReset() {
        actualTest.testActivityScenarioIncReset()
    }
    // end-method-testActivityScenarioIncReset

    @UiThreadTest
    fun testActivityScenarioIncUntilFull() {
        actualTest.testActivityScenarioIncUntilFull()
    }

    @UiThreadTest
    fun testActivityScenarioRotation() {
        actualTest.testActivityScenarioRotation()
    }
}
/**
 * Creates an [ActivityInstrumentationTestCase2] for the [SkeletonActivity]
 * activity.
 */
