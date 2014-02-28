package edu.luc.etl.cs313.android.clickcounter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter;

/**
 * The top-level activity of the click counter. It serves as the adapter that
 * mediates between the click counter model and view, following the
 * Model-View-Adapter architectural pattern. In addition, it manages the
 * application's lifecycle.
 *
 * @author laufer
 */
public class ClickCounterActivity extends Activity {

    // TODO slider and additional textview for max counter value
    // TODO enable assertions

    private static String TAG = "clickcounter-android-activity";

    /**
     * Explicit dependency on the model. (The dependency on the view is implicit.)
     */
    private ClickCounterModel model;

    /**
     * Setter for the model.
     */
    public void setModel(final ClickCounterModel model) {
        this.model = model;
    }

    /**
     * Handles the semantic increment event. (Semantic as opposed to, say, a
     * concrete button press.) This and similar events are connected to the
     * corresponding onClick events (actual button presses) in the view itself,
     * usually with the help of the graphical layout editor; the connection also
     * shows up in the XML source of the view layout.
     *
     * @param view the event source
     */
    // begin-method-onIncrement
    public void onIncrement(final View view) {
        model.increment();
        updateView();
    }
    // end-method-onIncrement

    /**
     * Handles the semantic decrement event.
     *
     * @param view the event source
     */
    public void onDecrement(final View view) {
        model.decrement();
        updateView();
    }

    /**
     * Handles the semantic decrement event.
     *
     * @param view the event source
     */
    public void onReset(final View view) {
        model.reset();
        updateView();
    }

    /**
     * Updates the view from the model.
     */
    // begin-method-updateView
    protected void updateView() {
        final TextView valueView = (TextView) findViewById(R.id.textview_value);
        valueView.setText(Integer.toString(model.get()));
        // afford controls according to model state
        ((Button) findViewById(R.id.button_increment)).setEnabled(!model.isFull());
        ((Button) findViewById(R.id.button_decrement)).setEnabled(!model.isEmpty());
    }
    // end-method-updateView

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
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.start();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        // inject the (implicit) dependency on the view
        setContentView(R.layout.activity_click_counter);
        // self-inject the dependency on the model
        if (savedInstanceState == null) {
            Log.i(TAG, "creating new model");
            setModel(createModelFromClassName());
            restoreModelFromPrefs();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        updateView();
        playDefaultNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        // save the counter value externally in case we get destroyed
        // for lack of resources or back button
        saveModelToPrefs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    /**
     * Preserves the model state for situations such device rotation.
     */
    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putSerializable(getString(R.string.model_key), model);
    }

    /**
     * Restores the model state after situations such device rotation.
     */
    @Override
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
        model = (ClickCounterModel) savedInstanceState.getSerializable(getString(R.string.model_key));
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
    protected ClickCounterModel createModelFromClassName() {
        // catch checked exceptions
        try {
            // for flexibility, instantiate model based on externally configured class name
            final BoundedCounter model = Class
                    .forName(getResources().getString(R.string.model_class))
                    .asSubclass(BoundedCounter.class)
                    .getConstructor(Integer.TYPE, Integer.TYPE)
                    .newInstance(0, 10);
            // TODO set min/max from res/values/numbers like so
//                    .newInstance(R.integer.min_val, R.integer.max_val);
            final ClickCounterModel wrapper = new BoundedCounterWrapper(model);
            return wrapper;
        } catch (final Throwable ex) {
            Log.d(TAG, "checked exception while instantiating model", ex);
            // re-throw as unchecked exception
            throw new RuntimeException(ex);
        }
    }

    /**
     * Attempts to read the externally saved counter value and update the model.
     */
    protected void restoreModelFromPrefs() {
        Log.i(TAG, "restoring model from shared prefs");
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final int value = sharedPref.getInt(getString(R.string.value_key), model.get());
        for (int i = model.get(); i < value; i++) {
            model.increment();
        }
    }

    /**
     * Saves the counter value externally.
     */
    protected void saveModelToPrefs() {
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.value_key), model.get());
        editor.commit();
    }
}
