package com.larsbutler.gamedemo.math;

import com.larsbutler.gamedemo.math.State;

public class MathUtil {

    /**
     * Gravity, in pixels/second^2.
     */
    public static final double GRAVITY = 4000;
    public static final int XAXIS = 0;
    public static final int YAXIS = 1;

    /**
     * Initial velocity required to reach the given displacement (s).
     * 
     * This is based on the 5th 'suvat' equation. See:
     * http://en.wikipedia.org/wiki/Equations_of_motion#Classic_version
     * 
     * @param a Constant acceleration (should be a negative value)
     * @param s Displacement
     * @param v Final velocity
     */
    public static double u(double a, double s, double v) {
        return Math.sqrt(Math.pow(v, 2)  -2 * a * s);
    }

    public static double max(double... objects) {
        if (objects.length == 0) {
            throw new RuntimeException("Input to `max` cannot be an empty array");
        }

        double maxSoFar = objects[0];
        for (int i = 1; i < objects.length; i++) {
            if (objects[i] > maxSoFar) {
                maxSoFar = objects[i];
            }
        }
        return maxSoFar;
    }

    public static double min(double... objects) {
        if (objects.length == 0) {
            throw new RuntimeException("Input to `min` cannot be an empty array");
        }

        double minSoFar = objects[0];
        for (int i = 1; i < objects.length; i++) {
            if (objects[i] < minSoFar) {
                minSoFar = objects[i];
            }
        }
        return minSoFar;
    }

    /**
     * Given an alpha value in the interval [0.0, 1.0],
     * interpolate between the previous and current states
     * and get adjusted position for rendering.
     */
    public static double getRenderPosition(
            State prevState, State currentState, double alpha) {
        if (alpha < 0.0 || alpha > 1.0) {
            throw new IllegalArgumentException(
                    "Alpha must be in the interval [0.0, 1.0].");
        }
        return currentState.p * alpha + prevState.p * (1 - alpha);
    }
}
