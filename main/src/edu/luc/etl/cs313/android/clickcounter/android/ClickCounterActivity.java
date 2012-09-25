package edu.luc.etl.cs313.android.clickcounter.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import edu.luc.etl.cs313.android.clickcounter.R;
import edu.luc.etl.cs313.android.clickcounter.model.Counter;

/**
 * Top-level activity for the click counter.
 * @author laufer
 */
public class ClickCounterActivity extends Activity {

	// TODO slider and additional textview for max counter value
	// TODO enable assertions

	private static String TAG = "clickcounter-android-activity";

	/**
	 * Reference to our model instance.
	 */
	private ClickCounterAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		// set the view of this activity
		setContentView(R.layout.activity_click_counter);
		// create adapter instance
		adapter = new ClickCounterAdapter();
		// inject dependency on activity (for finding the view components)
		adapter.setActivity(this);
		// inject dependency on model
		adapter.setModel(createModelFromClassName());
		// initialize adapter
		adapter.onCreate();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_click_counter, menu);
        return true;
    }

    @Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");

		// start up adapter
		adapter.onStart();
	}

    protected Counter createModelFromClassName() {
		// catch checked exceptions
		try {
			// for flexibility, instantiate model based on externally configured
			// class name
			final Counter model = Class
					.forName(getResources().getString(R.string.model_class))
					.asSubclass(Counter.class).newInstance();
			// inject dependency on model
			return model;
		} catch (final Throwable ex) {
			Log.d(TAG, "checked exception while instantiating model", ex);
			// re-throw as unchecked exception
			throw new RuntimeException(ex);
		}
    }
}
