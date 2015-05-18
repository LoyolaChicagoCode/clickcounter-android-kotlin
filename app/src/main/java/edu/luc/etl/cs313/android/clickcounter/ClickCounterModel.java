package edu.luc.etl.cs313.android.clickcounter;

import java.io.Serializable;

/**
 * A bounded counter abstraction for the Android click counter.
 *
 * @author laufer
 */
public interface ClickCounterModel extends Serializable {

    /**
     * Increments the counter value. Precondition: counter is not full.
     */
    void increment();

    /**
     * Decrements the counter value. Precondition: counter is not empty.
     */
    void decrement();

    /**
     * Resets the counter value. Precondition: true. Postcondition: counter is
     * not empty.
     */
    void reset();

    /**
     * Returns the current counter value.
     *
     * @return the current counter value
     */
    int get();

    /**
     * Indicates whether the counter is full (at its maximum).
     *
     * @return whether the counter is full
     */
    boolean isFull();

    /**
     * Indicates whether the counter is empty (at its minimum).
     *
     * @return whether the counter is empty
     */
    boolean isEmpty();
}
