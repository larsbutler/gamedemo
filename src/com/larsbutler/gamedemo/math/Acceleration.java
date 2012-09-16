package com.larsbutler.gamedemo.math;

import com.larsbutler.gamedemo.math.State;

/**
 * Acceleration callback, to be applied to state during integration.
 *
 */
public abstract class Acceleration {

    public static final Acceleration GRAVITY = new Acceleration(null, 0) {

        public double eval() {

            return MathUtil.GRAVITY;  // distance/time^2
        }

    };

    public static final Acceleration [] GRAVITY_ONLY = {GRAVITY};

    public static final Acceleration [] EMPTY = {};

    protected State state;
    protected double t;

    public Acceleration(State state, double t) {
        this.state = state;
        this.t = t;
    }

    public abstract double eval();

    public static double sum(Acceleration[] accels) {
        double total = 0.0;
        for (Acceleration a : accels) {
            total += a.eval();
        }

        return total;
    }

}
