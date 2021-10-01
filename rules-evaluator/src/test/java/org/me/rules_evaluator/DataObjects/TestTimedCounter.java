package org.me.rules_evaluator.DataObjects;

import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class TestTimedCounter {

    @Test
    public void test() {
        TimedCounter timedCounter = new TimedCounter(4);
        Date now = new Date();
        Date nowMinusOne = new Date(now.getTime() - (60L * 1000L));
        Date nowMinusThree = new Date(now.getTime() - (3 * 60L * 1000L));

        timedCounter.add(now);
        timedCounter.add(nowMinusThree);
        timedCounter.add(nowMinusOne);
        timedCounter.add(nowMinusThree);
        timedCounter.add(now);
        timedCounter.add(nowMinusThree);

        Integer[] report = timedCounter.reportCounts(5, now);
        assertArrayEquals(new Integer[] {2, 1, 0, 3, 0}, report);

        Integer[] report2 = timedCounter.reportCounts(5, nowMinusOne);
        assertArrayEquals(new Integer[] {1, 0, 3, 0, 0}, report2);
    }

    @Test
    public void testSizeOverflow() {
        TimedCounter timedCounter = new TimedCounter(3);
        Date now = new Date();
        Date nowMinusOne = new Date(now.getTime() - (60L * 1000L));
        Date nowMinusTwo = new Date(now.getTime() - (2 *  60L * 1000L));
        Date nowMinusThree = new Date(now.getTime() - (3 * 60L * 1000L));

        timedCounter.add(nowMinusThree);
        timedCounter.add(nowMinusTwo);
        timedCounter.add(nowMinusOne);

        Integer[] report = timedCounter.reportCounts(5, now);
        assertArrayEquals(new Integer[] {0, 1, 1, 1, 0}, report);

        timedCounter.add(now);

        Integer[] report2 = timedCounter.reportCounts(5, now);
        assertArrayEquals(new Integer[] {1, 1, 1, 0, 0}, report2);
    }
}
