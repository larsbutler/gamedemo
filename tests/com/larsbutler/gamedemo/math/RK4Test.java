package com.larsbutler.gamedemo.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.larsbutler.gamedemo.math.RK4;
import com.larsbutler.gamedemo.math.State;

public class RK4Test {

    /**
     * Conservative float precision delta (for floating point equality comparisons).
     */
    public static final double PREC_DELTA = 0.000000009;

    /**
     * The goal of this test is to demonstrate that simulations are
     * consistent regardless of the update frequency.
     * 
     * This simulation is a basic 'falling' scenario; we just apply
     * gravitational acceleration downwards.
     */
    @Test
    public void testFallingVariableTimestep() {
        final double startPos = 200;
        final double finalPos = 77.5;  // expected final position

        final double tTotal = 5;  // We simulate for a total of 5 seconds
        Acceleration gravity = new Acceleration(null, 0) {

            public double eval() {
                return -9.8;  // meters/sec^2
            }

        };
        Acceleration [] gravityOnly = {gravity};
        
        double [] dts = {0.001, 0.01, 0.1, 0.003, 0.03, 0.3, 0.2, 0.002};

        for (double dt : dts) {
            State state = new State();
            state.p = startPos;
            state.v = 0;

            double t = 0;
            for (; t < tTotal; t += dt) {
                state = RK4.integrate(state, t, dt, gravityOnly);
            }
            // simulate for the remaining time:
            // (there may be a partial cycle left)
            state = RK4.integrate(state, t, tTotal - t, gravityOnly);

            assertEquals(finalPos, state.p, PREC_DELTA);
        }
    }

    @Test
    public void testIntegrateDoesNotModifyInputState() {
        State initState = new State();
        initState.p = 100;
        initState.v = 0;

        State newState = RK4.integrate(initState, 0, 0.001, Acceleration.GRAVITY_ONLY);

        // Test that the initial state was not changed:
        assertEquals(100, initState.p, PREC_DELTA);
        assertEquals(0, initState.v, PREC_DELTA);

        // First, `integrate` should create a new State object for the result.
        assertNotSame(initState, newState);
        // Just to be thorough, check the new state to make sure that
        // __something__ changed:
        assertTrue(Double.compare(initState.p, newState.p) != 0);
        assertTrue(Double.compare(initState.v, newState.v) != 0);
    }
}
