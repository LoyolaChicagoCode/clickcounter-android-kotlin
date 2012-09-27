package edu.luc.etl.cs313.android.clickcounter.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.clickcounter.R;
import edu.luc.etl.cs313.android.clickcounter.model.Counter;

/**
 * The top-level activity of the click counter. It serves as the adapter that
 * mediates between the click counter model and view, following the
 * Model-View-Adapter architectural pattern.
 *
 * @author laufer
 */
public class ClickCounterAdapter extends Activity {

	// TODO slider and additional textview for max counter value
	// TODO enable assertions

	private static String TAG = "clickcounter-android-activity";

	/**
	 * Explicit dependency on the model. (The dependency on the view is
	 * implicit.)
	 */
	private Counter model;

	/**
	 * Setter for the model.
	 */
	public void setModel(final Counter model) {
		this.model = model;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		// inject the (implicit) dependency on the view
		setContentView(R.layout.activity_click_counter);
		// self-inject the dependency on the model
		setModel(createModelFromClassName());
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		updateView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.activity_click_counter, menu);
		return true;
	}

	/**
	 * Creates a model instance from the class name provided as the string value
	 * of the external model_class resource.
	 *
	 * @return
	 */
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

	/**
	 * Handles the semantic increment event. (Semantic as opposed to, say, a
	 * concrete button press.) This and similar events are connected to the
	 * corresponding onClick events (actual button presses) in the view itself,
	 * usually with the help of the graphical layout editor; the connection also
	 * shows up in the XML source of the view layout.
	 *
	 * @param view
	 *            the event source
	 */
	public void onIncrement(final View view) {
		model.increment();
		updateView();
	}

	/**
	 * Handles the semantic decrement event.
	 *
	 * @param view
	 *            the event source
	 */
	public void onDecrement(final View view) {
		model.decrement();
		updateView();
	}

	/**
	 * Handles the semantic decrement event.
	 *
	 * @param view
	 *            the event source
	 */
	public void onReset(final View view) {
		model.reset();
		updateView();
	}

	/**
	 * Updates the view from the model.
	 */
	protected void updateView() {
		final TextView valueView = (TextView) findViewById(R.id.textview_value);
		valueView.setText(Integer.toString(model.get()));
		// afford controls according to model state
		((Button) findViewById(R.id.button_increment)).setEnabled(!model.isFull());
		((Button) findViewById(R.id.button_decrement)).setEnabled(!model.isEmpty());
	}
}
