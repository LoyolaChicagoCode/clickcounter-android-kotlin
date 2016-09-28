package edu.luc.etl.cs313.android.clickcounter

import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter

/** Adapter for using the CLI bounded counter as the Android click counter model.  */
class BoundedCounterWrapper(val counter: BoundedCounter) : ClickCounterModel {

    override fun increment() {
        counter.increment()
    }

    override fun decrement() {
        counter.decrement()
    }

    override fun reset() {
        while (!counter.isEmpty) {
            counter.decrement()
        }
    }

    override fun get(): Int {
        return counter.get()
    }

    override val isFull: Boolean
        get() = counter.isFull

    override val isEmpty: Boolean
        get() = counter.isEmpty
}
