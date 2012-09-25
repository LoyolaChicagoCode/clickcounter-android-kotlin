package edu.luc.etl.cs313.android.clickcounter.android;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.clickcounter.R;
import edu.luc.etl.cs313.android.clickcounter.model.Counter;

/**
 * Adapter between click counter model and view, following the
 * Model-View-Adapter architectural pattern.
 * @author laufer
 */
public class ClickCounterAdapter {

	private static String TAG = "clickcounter-android-adapter";

	/**
	 * Dependency on our model instance.
	 */
	private Counter model;

	/**
	 * Dependency on our activity instance
	 * (required for finding the view components).
	 */
	private Activity activity;

	/**
	 * Injects the model instance.
	 */
	public void setModel(final Counter model) {
		this.model = model;
	}

	/**
	 * Injects the activity instance.
	 */
	public void setActivity(final Activity activity) {
		this.activity = activity;
	}

	public void onStart() {
		Log.i(TAG, "onStart");

		updateView();
	}

	/**
	 * Creates a listener that updates the view after executing a task.
	 * @param r the task to be performed before updating the view
	 * @return the created listener
	 */
	protected void createUpdateViewListener(final int id, final Runnable r) {
		findViewById(id).setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				r.run();
				updateView();
			}
		});
	}

	/**
	 * Configures this adapter by creating the required listeners.
	 */
	public void onCreate() {
		createUpdateViewListener(R.id.button_increment, new Runnable() {
			public void run() { model.increment(); }
		});
		createUpdateViewListener(R.id.button_decrement, new Runnable() {
			public void run() { model.decrement(); }
		});
		createUpdateViewListener(R.id.button_reset, new Runnable() {
			public void run() { model.reset(); }
		});
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

	protected View findViewById(final int id) { return activity.findViewById(id); }
}
