package edu.luc.etl.cs313.misc.boundedcounter.model

import edu.luc.etl.cs313.android.clickcounter.BuildConfig
import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter

/**
 * A simple bounded counter implementation.
 */
class SimpleBoundedCounter @JvmOverloads constructor(val min: Int = 0, val max: Int = 10) : BoundedCounter {
/**
 * Constructs a bounded counter with the given bounds.

 * @param min the lower bound
 * *
 * @param max the upper bound
 * *
 * @throws IllegalArgumentException if min > max
 */

    /** The current value of the counter.  */
    private var value: Int = 0

    init {
        if (min >= max) {
            throw IllegalArgumentException("min >= max")
        }
        this.value = this.min
    }

    /**
     * Indicates whether this counter currently satisfies its internal data
     * invariant: the counter value is within the bounds.

     * @return whether the data invariant is satisfied
     */
    protected fun dataInvariant(): Boolean {
        return min <= value && value <= max
    }

    override fun increment() {
        assertIfDebug({ dataInvariant() && !isFull })
        ++value
        assertIfDebug({ this.dataInvariant() })
    }

    override fun decrement() {
        assertIfDebug({ dataInvariant() && !isEmpty })
        --value
        assertIfDebug({ this.dataInvariant() })
    }

    override fun get(): Int {
        return value
    }

    override val isFull: Boolean
        get() = value >= max

    override val isEmpty: Boolean
        get() = value <= min

    protected fun assertIfDebug(p: () -> Boolean) {
        if (BuildConfig.DEBUG && !p()) {
            throw AssertionError()
        }
    }
}
/** Constructs a bounded counter with the default bounds.  */
