package edu.luc.etl.cs313.android.clickcounter.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Concrete testcase subclass for the default bounded counter implementation.
 * 
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public class DefaultBoundedCounterTest extends AbstractCounterTest {

	private final static int MIN = 0;

	private final static int MAX = 5;

	@Before
	public void setUp() throws Exception {
		setCounter(new DefaultBoundedCounter(MIN, MAX));
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
	public void testFullAtMax() {
		while (!counter.isFull())
			counter.increment();
		assertEquals(MAX, counter.get());
	}

	@Test
	public void testEmptyAtMin() {
		while (!counter.isEmpty())
			counter.decrement();
		assertEquals(MIN, counter.get());
	}

}
