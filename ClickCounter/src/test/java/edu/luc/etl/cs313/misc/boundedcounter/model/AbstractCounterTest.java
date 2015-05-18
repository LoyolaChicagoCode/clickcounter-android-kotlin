package edu.luc.etl.cs313.misc.boundedcounter.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testcase superclass for the counter abstraction.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractCounterTest {

    protected SimpleBoundedCounter counter;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param counter
     */
    protected void setCounter(final SimpleBoundedCounter counter) {
        this.counter = counter;
    }

    @Test
    public void testPreconditions() {
        // this counter has at least two different values
        assertFalse(counter.isEmpty() && counter.isFull());
    }

    // begin-method-testIncrement
    @Test
    public void testIncrement() {
        decrementIfFull();
        assertFalse(counter.isFull());
        final int v = counter.get();
        counter.increment();
        assertEquals(v + 1, counter.get());
    }
    // end-method-testIncrement

    @Test
    public void testDecrement() {
        incrementIfEmpty();
        assertFalse(counter.isEmpty());
        final int v = counter.get();
        counter.decrement();
        assertEquals(v - 1, counter.get());
    }

    @Test
    public void testGet() {
        // cannot check much other than consistency across invocations
        final int v = counter.get();
        assertEquals(v, counter.get());
    }

    @Test
    public void testIsFull() {
        decrementIfFull();
        assertFalse(counter.isFull());
    }

    @Test
    public void testIsEmpty() {
        incrementIfEmpty();
        assertFalse(counter.isEmpty());
    }

    protected void decrementIfFull() {
        if (counter.isFull()) {
            counter.decrement();
        }
    }

    protected void incrementIfEmpty() {
        if (counter.isEmpty()) {
            counter.increment();
        }
    }
}
