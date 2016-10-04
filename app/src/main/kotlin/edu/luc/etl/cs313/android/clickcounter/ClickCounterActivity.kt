package edu.luc.etl.cs313.android.clickcounter

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View

import java.io.IOException

import kotlinx.android.synthetic.main.activity_click_counter.*

import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter
import edu.luc.etl.cs313.misc.boundedcounter.model.SimpleBoundedCounter

/**
 * The top-level activity of the click counter. It serves as the adapter that
 * mediates between the click counter model and view, following the
 * Model-View-Adapter architectural pattern. In addition, it manages the
 * application's lifecycle.

 * @author laufer
 */
class ClickCounterActivity : Activity() {

    /** Explicit dependency on the model. (The dependency on the view is implicit.)  */
    private var model: ClickCounterModel? = null

    /** Setter for the model.  */
    fun setModel(model: ClickCounterModel) {
        this.model = model
    }

    /**
     * Handles the semantic increment event. (Semantic as opposed to, say, a
     * concrete button press.) This and similar events are connected to the
     * corresponding onClick events (actual button presses) in the view itself,
     * usually with the help of the graphical layout editor; the connection also
     * shows up in the XML source of the view layout.

     * @param view the event source
     */
    // begin-method-onIncrement
    fun onIncrement(view: View) {
        model!!.increment()
        updateView()
    }
    // end-method-onIncrement

    /**
     * Handles the semantic decrement event.

     * @param view the event source
     */
    fun onDecrement(view: View) {
        model!!.decrement()
        updateView()
    }

    /**
     * Handles the semantic decrement event.

     * @param view the event source
     */
    fun onReset(view: View) {
        model!!.reset()
        updateView()
    }

    /**
     * Updates the view from the model.
     */
    // begin-method-updateView
    protected fun updateView() {
        textview_value.text = Integer.toString(model!!.get())
        // afford controls according to model state
        button_increment.isEnabled = !model!!.isFull
        button_decrement.isEnabled = !model!!.isEmpty
    }
    // end-method-updateView
    // TODO consider beeping when max is reached

    /**
     * Plays the default notification sound.
     */
    protected fun playDefaultNotification() {
        val defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mediaPlayer = MediaPlayer()
        val context = applicationContext

        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
            mediaPlayer.prepare()
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.start()
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        // inject the (implicit) dependency on the view
        setContentView(R.layout.activity_click_counter)
        // self-inject the dependency on the model
        if (savedInstanceState == null) {
            Log.i(TAG, "creating new model")
            setModel(BoundedCounterWrapper(SimpleBoundedCounter()))
            restoreModelFromPrefs()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        updateView()
        playDefaultNotification()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
        // save the counter value externally in case we get destroyed
        // for lack of resources or back button
        saveModelToPrefs()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    public override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.i(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.activity_click_counter, menu)
        return true
    }

    /**
     * Creates a model instance from the class name provided as the string value
     * of the external model_class resource.

     * @return the model instance
     */
    protected fun createModelFromClassName(): ClickCounterModel {
        // catch checked exceptions
        try {
            // for flexibility, instantiate model based on externally configured class name
            val model = Class.forName(resources.getString(R.string.model_class)).asSubclass(BoundedCounter::class.java).getConstructor(Integer.TYPE, Integer.TYPE).newInstance(0, 10)
            // TODO set min/max from res/values/numbers like so
            //                    .newInstance(R.integer.min_val, R.integer.max_val);
            return BoundedCounterWrapper(model)
        } catch (ex: Throwable) {
            Log.d(TAG, "checked exception while instantiating model", ex)
            // re-throw as unchecked exception
            throw RuntimeException(ex)
        }

    }

    /** Attempts to read the externally saved counter value and update the model.   */
    protected fun restoreModelFromPrefs() {
        Log.i(TAG, "restoring model from shared prefs")
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val value = sharedPref.getInt(getString(R.string.value_key), model!!.get())
        for (i in model!!.get()..value - 1) {
            model!!.increment()
        }
    }

    /** Saves the counter value externally.  */
    protected fun saveModelToPrefs() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(getString(R.string.value_key), model!!.get())
        editor.commit()
    }

    companion object {

        // TODO slider and additional textview for max counter value
        // TODO enable assertions

        private val TAG = "clickcounter-android-activity"
    }
}
