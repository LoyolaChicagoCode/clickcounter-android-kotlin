package edu.luc.etl.cs313.misc.boundedcounter.cli

/** A bounded counter abstraction.  */
// begin-interface-BoundedCounter
interface BoundedCounter {
    fun increment()
    fun decrement()
    fun get(): Int
    val isFull: Boolean
    val isEmpty: Boolean
}
// end-interface-BoundedCounter
