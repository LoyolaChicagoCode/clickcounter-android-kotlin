package edu.luc.etl.cs313.android.clickcounter

import java.io.Serializable

/**
 * A bounded counter abstraction for the Android click counter.

 * @author laufer
 */
interface ClickCounterModel : Serializable {

    /** Increments the counter value. Precondition: counter is not full.  */
    fun increment()

    /** Decrements the counter value. Precondition: counter is not empty.  */
    fun decrement()

    /**
     * Resets the counter value. Precondition: true. Postcondition: counter is
     * not empty.
     */
    fun reset()

    /**
     * Returns the current counter value.

     * @return the current counter value
     */
    fun get(): Int

    /**
     * Indicates whether the counter is full (at its maximum).

     * @return whether the counter is full
     */
    val isFull: Boolean

    /**
     * Indicates whether the counter is empty (at its minimum).

     * @return whether the counter is empty
     */
    val isEmpty: Boolean
}
