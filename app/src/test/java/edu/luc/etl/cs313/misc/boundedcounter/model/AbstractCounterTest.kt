package edu.luc.etl.cs313.misc.boundedcounter.model

import org.junit.Test

import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

/**
 * Testcase superclass for the counter abstraction.

 * @author laufer
 * *
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
abstract class AbstractCounterTest {

    protected abstract var counter: BoundedCounter

    @Test
    fun testPreconditions() {
        // this counter has at least two different values
        assertFalse(counter.isEmpty && counter.isFull)
    }

    // begin-method-testIncrement
    @Test
    open fun testIncrement() {
        decrementIfFull()
        assertFalse(counter.isFull)
        val v = counter.get()
        counter.increment()
        assertEquals((v + 1).toLong(), counter.get().toLong())
    }
    // end-method-testIncrement

    @Test
    open fun testDecrement() {
        incrementIfEmpty()
        assertFalse(counter.isEmpty)
        val v = counter.get()
        counter.decrement()
        assertEquals((v - 1).toLong(), counter.get().toLong())
    }

    @Test
    fun testGet() {
        // cannot check much other than consistency across invocations
        val v = counter.get()
        assertEquals(v.toLong(), counter.get().toLong())
    }

    @Test
    fun testIsFull() {
        decrementIfFull()
        assertFalse(counter.isFull)
    }

    @Test
    fun testIsEmpty() {
        incrementIfEmpty()
        assertFalse(counter.isEmpty)
    }

    protected fun decrementIfFull() {
        if (counter.isFull) {
            counter.decrement()
        }
    }

    protected fun incrementIfEmpty() {
        if (counter.isEmpty) {
            counter.increment()
        }
    }
}
