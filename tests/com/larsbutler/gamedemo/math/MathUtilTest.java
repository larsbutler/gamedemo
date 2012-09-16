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
}
