package org.me.rules_evaluator.DataObjects;

import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class TestTimedCounter {

    @Test
    public void test() {
        TimedCounter timedCounter = new TimedCounter();
        Date now = new Date();
        Date nowMinusOne = new Date(now.getTime() - (    60L * 1000L));
        Date nowMinusTwo = new Date(now.getTime() - (2 * 60L * 1000L));

        timedCounter.add(now);
        timedCounter.add(nowMinusTwo);
        timedCounter.add(nowMinusOne);
        timedCounter.add(nowMinusTwo);
        timedCounter.add(now);
        timedCounter.add(nowMinusTwo);

        Integer[] report = timedCounter.reportCounts(5, now);
        assertArrayEquals(new Integer[] {2, 1, 3, 0, 0}, report);
    }
}
