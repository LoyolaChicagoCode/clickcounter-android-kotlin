package edu.luc.etl.cs313.android.clickcounter.android;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
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
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		// inject the (implicit) dependency on the view
		setContentView(R.layout.activity_click_counter);
		// self-inject the dependency on the model
		setModel(createModelFromClassName());
		// attempt to read the externally saved counter value and update the model
		final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		final int value = sharedPref.getInt(getString(R.string.value_key), model.get());
		for (int i = model.get(); i < value; i ++) { model.increment(); }
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		updateView();
	}

	/**
	 * Preserves the model state for situations such device rotation.
	 */
	@Override
	public void onSaveInstanceState(final Bundle savedInstanceState) {
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putSerializable(getString(R.string.model_key), model);
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * Restores the model state after situations such device rotation.
	 */
	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		Log.i(TAG, "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
		model = (Counter) savedInstanceState.getSerializable(getString(R.string.model_key));
		updateView();
		playDefaultNotification();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		// save the counter value externally
		final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.value_key), model.get());
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
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

	/**
	 * Plays the default notification sound.
	 */
	protected void playDefaultNotification() {
		final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		final MediaPlayer mediaPlayer = new MediaPlayer();
		final Context context = getApplicationContext();

		try {
			mediaPlayer.setDataSource(context, defaultRingtoneUri);
		    mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
		    mediaPlayer.prepare();
		    mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
		        @Override public void onCompletion(MediaPlayer mp) {
		            mp.release();
		        }
		    });
		    mediaPlayer.start();
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
