package edu.luc.etl.cs313.android.clickcounter;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

/**
 * Concrete Android test subclass. Has to inherit from framework class
 * and uses delegation to concrete subclass of abstract test superclass.
 * IMPORTANT: project must export JUnit 4 to make it available on the
 * device.
 *
 * @author laufer
 * @see http://developer.android.com/tools/testing/activity_testing.html
 */
public class ClickCounterActivityTest extends ActivityInstrumentationTestCase2<ClickCounterActivity> {

    /**
     * Creates an {@link ActivityInstrumentationTestCase2} for the {@link SkeletonActivity}
     * activity.
     */
    public ClickCounterActivityTest() {
        super(ClickCounterActivity.class);
        actualTest = new AbstractClickCounterActivityTest() {
            @Override
            protected ClickCounterActivity getActivity() {
                // return activity instance provided by instrumentation test
                return ClickCounterActivityTest.this.getActivity();
            }
        };
    }

    // test subclass instance to delegate to
    private AbstractClickCounterActivityTest actualTest;

    public void testActivityTestCaseSetUpProperly() {
        actualTest.testActivityTestCaseSetUpProperly();
    }

    // begin-method-testActivityScenarioIncReset
    @UiThreadTest
    public void testActivityScenarioIncReset() {
        actualTest.testActivityScenarioIncReset();
    }
    // end-method-testActivityScenarioIncReset

    @UiThreadTest
    public void testActivityScenarioIncUntilFull() {
        actualTest.testActivityScenarioIncUntilFull();
    }

    @UiThreadTest
    public void testActivityScenarioRotation() {
        actualTest.testActivityScenarioRotation();
    }
}
