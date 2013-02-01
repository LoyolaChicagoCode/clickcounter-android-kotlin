package edu.luc.etl.cs313.android.clickcounter.android;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.util.Log;

/**
 * Concrete Robolectric test subclass.
 *
 * @author laufer
 * @see http://pivotal.github.com/robolectric
 */
@RunWith(RobolectricTestRunner.class)
public class ClickCounterActivityRobolectric extends ClickCounterActivityTest {

	private static String TAG = "clickcounter-android-activity-robolectric";

	private ClickCounterAdapter activity;

	@Before
	public void setUp() {
		activity = new ClickCounterAdapter();
		activity.onCreate(null);
		activity.onStart();
		Log.d(TAG, "setting up test...");
	}

	protected ClickCounterAdapter getActivity() {
		return activity;
	}
}
