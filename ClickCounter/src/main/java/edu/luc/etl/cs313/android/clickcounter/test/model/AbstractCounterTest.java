package edu.luc.etl.cs313.android.clickcounter.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.luc.etl.cs313.android.clickcounter.model.Counter;

/**
 * Testcase superclass for the counter abstraction.
 * 
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractCounterTest {

	protected Counter counter;

	/**
	 * Setter for dependency injection. Usually invoked by concrete testcase
	 * subclass.
	 * 
	 * @param counter
	 */
	protected void setCounter(final Counter counter) {
		this.counter = counter;
	}

	@Test
	public void testPreconditions() {
		// this counter has at least two different values
		assertFalse(counter.isEmpty() && counter.isFull());
	}

	@Test
	public void testIncrement() {
		decrementIfFull();
		assertFalse(counter.isFull());
		final int v = counter.get();
		counter.increment();
		assertEquals(v + 1, counter.get());
	}

	@Test
	public void testDecrement() {
		incrementIfEmpty();
		assertFalse(counter.isEmpty());
		final int v = counter.get();
		counter.decrement();
		assertEquals(v - 1, counter.get());
	}

	@Test
	public void testReset() {
		incrementIfEmpty();
		assertFalse(counter.isEmpty());
		counter.reset();
		assertTrue(counter.isEmpty());
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
		if (counter.isFull()){ counter.decrement(); }
	}
	
	protected void incrementIfEmpty() {
		if (counter.isEmpty()) { counter.increment(); }
	}
}
