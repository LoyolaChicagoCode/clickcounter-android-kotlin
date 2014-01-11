package edu.luc.etl.cs313.misc.boundedcounter.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Concrete testcase subclass for the default bounded counter implementation.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public class SimpleBoundedCounterTest extends AbstractCounterTest {

    private final static int MIN = 0;

    private final static int MAX = 5;

    @Before
    public void setUp() throws Exception {
        setCounter(new SimpleBoundedCounter(MIN, MAX));
    }

    @After
    public void tearDown() throws Exception {
        setCounter(null);
    }

    @Test
    public void testInitiallyAtMin() {
        assertEquals(MIN, counter.get());
    }

    @Test
    public void testIncrement() {
        final int v = counter.get();
        counter.increment();
        assertEquals(v + 1, counter.get());
        assertFalse(counter.isEmpty());
    }

    protected void makeEmpty() {
        while (!counter.isEmpty())
            counter.decrement();
    }

    protected void makeFull() {
        while (!counter.isFull())
            counter.increment();
    }

    @Test
    public void testFullAtMax() {
        makeFull();
        assertEquals(MAX, counter.get());
    }

    @Test
    public void testEmptyAtMin() {
        makeEmpty();
        assertEquals(MIN, counter.get());
    }

    @Test
    public void testDecrement() {
        makeFull();
        final int v = counter.get();
        counter.decrement();
        assertEquals(v - 1, counter.get());
        assertFalse(counter.isFull());
    }
}
