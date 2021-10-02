package org.me.rules_evaluator.RulesChecker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRulesCheckerReport {

    @Test
    public void testAdd() {
        RulesCheckerReport rulesCheckerReport = new RulesCheckerReport(3);

        rulesCheckerReport.addComponent("C1");
        rulesCheckerReport.addTypeReport("C1", "T1", new Integer[] {1, 2, 3});
        rulesCheckerReport.addTypeReport("C1", "T2", new Integer[] {10, 20, 30});
        rulesCheckerReport.addTypeReport("C1", "T3", new Integer[] {100, 200, 300});

        rulesCheckerReport.addComponent("C2");
        rulesCheckerReport.addTypeReport("C2", "T1", new Integer[] {4, 5, 6});

        rulesCheckerReport.addComponent("C3");

        rulesCheckerReport.finish();

        assertArrayEquals(new Integer[] {1, 3, 6}, rulesCheckerReport.report.get("C1").get("T1"));
        assertArrayEquals(new Integer[] {10, 30, 60}, rulesCheckerReport.report.get("C1").get("T2"));
        assertArrayEquals(new Integer[] {100, 300, 600}, rulesCheckerReport.report.get("C1").get("T3"));
        assertArrayEquals(new Integer[] {111, 333, 666}, rulesCheckerReport.reportComponents.get("C1"));

        assertArrayEquals(new Integer[] {4, 9, 15}, rulesCheckerReport.report.get("C2").get("T1"));
        assertArrayEquals(new Integer[] {4, 9, 15}, rulesCheckerReport.reportComponents.get("C2"));

        assertArrayEquals(new Integer[] {0, 0, 0}, rulesCheckerReport.reportComponents.get("C3"));
    }


    @Test
    public void testSum() {
        RulesCheckerReport rulesCheckerReport = new RulesCheckerReport(3);

        assertEquals(3, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 2, 0));
        assertEquals(5, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 2, 1));
        assertEquals(3, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 2, 2));
        assertEquals(0, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 2, 3));
        assertEquals(0, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 2, 4));

        assertEquals(1, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 1, 0));
        assertEquals(2, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 1, 1));
        assertEquals(3, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 1, 2));

        assertEquals(6, rulesCheckerReport.sumFromArray(new Integer[] {1, 3, 6}, 3, 0));
    }

    @Test
    public void testRate() {
        RulesCheckerReport rulesCheckerReport = new RulesCheckerReport(3);

        assertEquals(1, rulesCheckerReport.rateFromArray(new Integer[] {1, 3, 6}, 2, 0));
        assertEquals(2, rulesCheckerReport.rateFromArray(new Integer[] {1, 3, 6}, 2, 1));
        assertEquals(1, rulesCheckerReport.rateFromArray(new Integer[] {1, 3, 6}, 2, 2));

        assertEquals(2, rulesCheckerReport.maxRateFromArray(new Integer[] {1, 3, 6}, 3));
        assertEquals(2, rulesCheckerReport.maxRateFromArray(new Integer[] {1, 3, 6}, 2));
        assertEquals(3, rulesCheckerReport.maxRateFromArray(new Integer[] {1, 3, 6}, 1));
    }
}
