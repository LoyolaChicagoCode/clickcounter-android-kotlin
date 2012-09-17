package edu.luc.etl.cs313.android.clickcounter.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import edu.luc.etl.cs313.android.clickcounter.R;
import edu.luc.etl.cs313.android.clickcounter.model.Counter;

public class ClickCounterController extends Activity {

	// TODO slider and additional textview for max counter value
	// TODO properly center textviews
	// TODO unit and integration tests
	// TODO enable assertions

	private static String TAG = "clickcounter-android";

	/**
	 * Reference to our model instance.
	 */
	private Counter model;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");

		configureApp();
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

		updateView();
	}

	/**
	 * Instantiates model and connects listeners to views.
	 */
	protected void configureApp() {
		setContentView(R.layout.activity_click_counter);

		// catch checked exceptions
		try {
			// for flexibility, instantiate model based on externally configured
			// class name
			model = Class
					.forName(getResources().getString(R.string.model_class))
					.asSubclass(Counter.class).newInstance();
		} catch (final Throwable ex) {
			Log.d(TAG, "checked exception while instantiating model", ex);
			// re-throw as unchecked exception
			throw new RuntimeException(ex);
		}

		final OnClickListener incrementListener = new OnClickListener() {
			public void onClick(final View v) {
				model.increment();
				updateView();
			}
		};
		findViewById(R.id.button_increment).setOnClickListener(
				incrementListener);
		final OnClickListener decrementListener = new OnClickListener() {
			public void onClick(final View v) {
				model.decrement();
				updateView();
			}
		};
		findViewById(R.id.button_decrement).setOnClickListener(
				decrementListener);
		final OnClickListener resetListener = new OnClickListener() {
			public void onClick(final View v) {
				model.reset();
				updateView();
			}
		};
		findViewById(R.id.button_reset).setOnClickListener(resetListener);
	}

	/**
	 * Updates the view from the model.
	 */
	protected void updateView() {
		final TextView valueView = (TextView) findViewById(R.id.textview_value);
		valueView.setText(Integer.toString(model.get()));
		// afford controls according to model state
		((Button) findViewById(R.id.button_increment)).setEnabled(!model
				.isFull());
		((Button) findViewById(R.id.button_decrement)).setEnabled(!model
				.isEmpty());
	}
}
