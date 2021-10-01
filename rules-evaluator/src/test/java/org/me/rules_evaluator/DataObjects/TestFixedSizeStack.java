package org.me.rules_evaluator.DataObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFixedSizeStack {

    @Test
    public void testPushAndRead() {
        FixedSizeStack fixedSizeStack = new FixedSizeStack(3);

        fixedSizeStack.push("Element 1");
        fixedSizeStack.push("Element 2");
        assertArrayEquals(
                new String[] {"Element 2"},
                fixedSizeStack.read(1)
        );
        assertArrayEquals(
                new String[] {"Element 2", "Element 1"},
                fixedSizeStack.read(2)
        );
        assertArrayEquals(
                new String[] {"Element 2", "Element 1"},
                fixedSizeStack.read(3)
        );

        fixedSizeStack.push("Element 3");
        fixedSizeStack.push("Element 4");
        fixedSizeStack.push("Element 5");
        assertArrayEquals(
                new String[] {"Element 5", "Element 4", "Element 3"},
                fixedSizeStack.read(3)
        );
    }


    @Test
    public void testGetSize() {
        FixedSizeStack fixedSizeStack = new FixedSizeStack(3);
        assertEquals(0, fixedSizeStack.getSize());

        fixedSizeStack.push("Element 1");
        assertEquals(1, fixedSizeStack.getSize());

        fixedSizeStack.push("Element 2");
        assertEquals(2, fixedSizeStack.getSize());

        fixedSizeStack.push("Element 3");
        assertEquals(3, fixedSizeStack.getSize());

        fixedSizeStack.push("Element 4");
        assertEquals(3, fixedSizeStack.getSize());

        fixedSizeStack.push("Element 5");
        assertEquals(3, fixedSizeStack.getSize());
    }
}
