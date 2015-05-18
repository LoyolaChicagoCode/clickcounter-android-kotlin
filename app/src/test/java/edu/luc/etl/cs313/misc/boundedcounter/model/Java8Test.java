package edu.luc.etl.cs313.misc.boundedcounter.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;

public class Java8Test {

    @Test
    public void testLambda() {
        final Comparator<Integer> c = (x, y) -> x - y;
        assertEquals(0, c.compare(3, 3));
    }
}
