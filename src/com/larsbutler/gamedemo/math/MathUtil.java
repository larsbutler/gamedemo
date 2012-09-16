package com.larsbutler.gamedemo.math;

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
}
