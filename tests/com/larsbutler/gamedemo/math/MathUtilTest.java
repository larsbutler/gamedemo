package com.larsbutler.gamedemo.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilTest {

    @Test
    public void testU() {
        double displacement = 200.0;
        double a = -0.5;
        double finalVel = 0.0;

        assertEquals(14.142135623730951, MathUtil.u(a, displacement, finalVel), 0.0);
    }

    @Test
    public void testU_2() {
        double displacement = 200.0;
        double a = -0.75;
        double finalVel = 0.0;
        assertEquals(17.320508075688775, MathUtil.u(a, displacement, finalVel), 0.0);
    }

    @Test(expected=RuntimeException.class)
    public void testMaxNoInput() {
        MathUtil.max();
    }

    @Test
    public void testMax() {
        assertEquals(84.444, MathUtil.max(-66.1, 3.3, 11.1, 5.555, -7, 84.444, 19.001), 0.0);
    }

    @Test
    public void testMax2() {
        assertEquals(84.444, MathUtil.max(84.444, -66.1, 3.3, 11.1, 5.555, -7, 19.001), 0.0);
    }

    @Test
    public void testMax3() {
        assertEquals(-1.444, MathUtil.max(-66.1, -3.3, -11.1, -5.555, -7, -19.001, -1.444), 0.0);
    }

    @Test(expected=RuntimeException.class)
    public void testMinNoInput() {
        MathUtil.min();
    }

    @Test
    public void testMin() {
        assertEquals(-19.001, MathUtil.min(66.1, 3.3, 11.1, 5.555, -7, 84.444, -19.001), 0.0);
    }

    @Test
    public void testMin2() {
        assertEquals(3.3, MathUtil.min(84.444, 66.1, 3.3, 11.1, 5.555, 7, 19.001), 0.0);
    }

    @Test
    public void testMin3() {
        assertEquals(-66.1, MathUtil.min(-66.1, -3.3, -11.1, -5.555, -7, -19.001, -1.444), 0.0);
    }

}
