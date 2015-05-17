package edu.luc.etl.cs313.misc.boundedcounter.cli;

/** A bounded counter abstraction. */
// begin-interface-BoundedCounter
public interface BoundedCounter {
    void increment();
    void decrement();
    int get();
    boolean isFull();
    boolean isEmpty();
}
// end-interface-BoundedCounter
