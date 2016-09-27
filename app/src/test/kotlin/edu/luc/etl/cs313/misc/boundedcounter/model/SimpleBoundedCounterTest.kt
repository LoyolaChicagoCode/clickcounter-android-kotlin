package edu.luc.etl.cs313.misc.boundedcounter.model

import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

/**
 * Concrete testcase subclass for the default bounded counter implementation.

 * @author laufer
 * *
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
class SimpleBoundedCounterTest : AbstractCounterTest() {

    override var counter: BoundedCounter = SimpleBoundedCounter(MIN, MAX)

    @Test
    fun testInitiallyAtMin() {
        assertEquals(MIN.toLong(), counter.get().toLong())
    }

    @Test
    override fun testIncrement() {
        val v = counter.get()
        counter.increment()
        assertEquals((v + 1).toLong(), counter.get().toLong())
        assertFalse(counter.isEmpty)
    }

    protected fun makeEmpty() {
        while (!counter.isEmpty)
            counter.decrement()
    }

    protected fun makeFull() {
        while (!counter.isFull)
            counter.increment()
    }

    @Test
    fun testFullAtMax() {
        makeFull()
        assertEquals(MAX.toLong(), counter.get().toLong())
    }

    @Test
    fun testEmptyAtMin() {
        makeEmpty()
        assertEquals(MIN.toLong(), counter.get().toLong())
    }

    @Test
    override fun testDecrement() {
        makeFull()
        val v = counter.get()
        counter.decrement()
        assertEquals((v - 1).toLong(), counter.get().toLong())
        assertFalse(counter.isFull)
    }

    companion object {

        private val MIN = 0

        private val MAX = 5
    }
}
