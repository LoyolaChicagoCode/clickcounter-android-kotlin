package edu.luc.etl.cs313.android.clickcounter.android;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.util.Log;

/**
 * Concrete Robolectric test subclass.
 *
 * @author laufer
 * @see http://pivotal.github.com/robolectric
 */
@RunWith(RobolectricTestRunner.class)
public class ClickCounterActivityRobolectric extends AbstractClickCounterActivityTest {

	private static String TAG = "clickcounter-android-activity-robolectric";

	private ClickCounterAdapter activity;

	@Before
	public void setUp() {
		activity = Robolectric.buildActivity(ClickCounterAdapter.class).create().get();
		activity.onCreate(null);
		activity.onStart();
		Log.d(TAG, "setting up test...");
	}

	@Override
	protected ClickCounterAdapter getActivity() {
		return activity;
	}
}
