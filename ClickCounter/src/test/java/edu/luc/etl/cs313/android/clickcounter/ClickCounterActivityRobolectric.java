package edu.luc.etl.cs313.android.clickcounter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import edu.luc.etl.cs313.android.clickcounter.ClickCounterActivity;

/**
 * Concrete Robolectric test subclass. For the Gradle unitTest task to work,
 * the Robolectric dependency needs to be isolated here instead of being present in src/main.
 *
 * @author laufer
 * @see http://pivotal.github.com/robolectric
 */
@RunWith(RobolectricTestRunner.class)
public class ClickCounterActivityRobolectric extends AbstractClickCounterActivityTest {

    private static String TAG = "clickcounter-android-activity-robolectric";

    private ClickCounterActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(ClickCounterActivity.class).create().start().get();
    }

    @Override
    protected ClickCounterActivity getActivity() {
        return activity;
    }
}
