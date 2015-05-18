package edu.luc.etl.cs313.misc.boundedcounter.model;

import edu.luc.etl.cs313.android.clickcounter.BuildConfig;
import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter;

/**
 * A simple bounded counter implementation.
 */
public class SimpleBoundedCounter implements BoundedCounter {

    /** The lower bound of the counter. */
    private final int min;

    /** The upper bound of the counter. */
    private final int max;

    /** The current value of the counter. */
    private int value;

    /** Constructs a bounded counter with the default bounds. */
    public SimpleBoundedCounter() {
        this(0, 10);
    }

    /**
     * Constructs a bounded counter with the given bounds.
     *
     * @param min the lower bound
     * @param max the upper bound
     * @throws IllegalArgumentException if min > max
     */
    public SimpleBoundedCounter(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min >= max");
        }
        this.min = min;
        this.max = max;
        this.value = this.min;
    }

    /**
     * Indicates whether this counter currently satisfies its internal data
     * invariant: the counter value is within the bounds.
     *
     * @return whether the data invariant is satisfied
     */
    protected boolean dataInvariant() {
        return min <= value && value <= max;
    }

    @Override
    public void increment() {
        assertIfDebug(() -> dataInvariant() && !isFull());
        ++value;
        assertIfDebug(() -> dataInvariant());
    }

    @Override
    public void decrement() {
        assertIfDebug(() -> dataInvariant() && !isEmpty());
        --value;
        assertIfDebug(() -> dataInvariant());
    }

    @Override
    public int get() {
        return value;
    }

    @Override
    public boolean isFull() {
        return value >= max;
    }

    @Override
    public boolean isEmpty() {
        return value <= min;
    }

    public interface BooleanSupplier {
        boolean getAsBoolean();
    }

    protected void assertIfDebug(final BooleanSupplier p) {
        if (BuildConfig.DEBUG && !p.getAsBoolean()) {
            throw new AssertionError();
        }
    }
}
