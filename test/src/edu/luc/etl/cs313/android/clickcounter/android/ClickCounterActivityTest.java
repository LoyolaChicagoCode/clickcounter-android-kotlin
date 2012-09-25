package edu.luc.etl.cs313.android.clickcounter.android;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.clickcounter.R;

/**
 * GUI-level test of several essential click-counter scenarios.
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
	}

    /**
     * Verifies that the activity under test can be launched.
     */
    public void testActivityTestCaseSetUpProperly() {
        assertNotNull("activity should be launched successfully", getActivity());
    }

    @UiThreadTest
    public void testActivityScenarioIncReset() {
    	assertEquals(0, getDisplayedValue());
    	assertTrue(getIncButton().isEnabled());
    	assertFalse(getDecButton().isEnabled());
    	assertTrue(getResetButton().isEnabled());
    	assertTrue(getIncButton().performClick());
    	assertEquals(1, getDisplayedValue());
    	assertTrue(getIncButton().isEnabled());
    	assertTrue(getDecButton().isEnabled());
    	assertTrue(getResetButton().isEnabled());
    	assertTrue(getResetButton().performClick());
    	assertEquals(0, getDisplayedValue());
    	assertTrue(getIncButton().isEnabled());
    	assertFalse(getDecButton().isEnabled());
    	assertTrue(getResetButton().isEnabled());
    }

    @UiThreadTest
    public void testActivityScenarioIncUntilFull() {
    	assertEquals(0, getDisplayedValue());
    	assertTrue(getIncButton().isEnabled());
    	assertFalse(getDecButton().isEnabled());
    	assertTrue(getResetButton().isEnabled());
    	while (getIncButton().isEnabled()) {
    		final int v = getDisplayedValue();
        	assertTrue(getIncButton().performClick());
        	assertEquals(v + 1, getDisplayedValue());
    	}
    	assertFalse(getIncButton().isEnabled());
    	assertTrue(getDecButton().isEnabled());
    	assertTrue(getResetButton().isEnabled());
    }

    // auxiliary methods for easy access to UI widgets

    protected int getDisplayedValue() {
    	final TextView t = (TextView) getActivity().findViewById(R.id.textview_value);
    	return Integer.parseInt(t.getText().toString().trim());
    }

    protected Button getIncButton() {
    	return (Button) getActivity().findViewById(R.id.button_increment);
    }

    protected Button getDecButton() {
    	return (Button) getActivity().findViewById(R.id.button_decrement);
    }

    protected Button getResetButton() {
    	return (Button) getActivity().findViewById(R.id.button_reset);
    }
}
