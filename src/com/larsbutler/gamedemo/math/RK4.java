package com.larsbutler.gamedemo.math;


/**
 * Functions for performing fourth order Runge-Kutta (RK4) integration.
 * Useful in physics simulations.
 *
 * The methods implemented here were heavily inspired by an article by
 * Glenn Fiedler: http://gafferongames.com/game-physics/integration-basics/
 * Design copied (and modified) with permission.
 */
public class RK4 {

    public static Derivative evaluate(
            State initial, double t, double dt, Derivative d, double accel) {

        State newState = new State();
        newState.p = initial.p + d.dp * dt;
        newState.v = initial.v + d.dv * dt;

        Derivative output = new Derivative();
        output.dp = newState.v;
        output.dv = accel;
        return output;
    }

    /**
     * Apply acceleration (accels) to a given state (state) for a given
     * time step (dt).
     * @param state
     * @param t
     * @param dt
     * @param accels
     * @return A new State object with the updated state
     */
    public static State integrate(State state, double t, double dt, Acceleration[] accels) {
        Derivative a, b, c, d;

        // The return value; we do not modify the input state,
        // since we want to keep it as the `previous state` of each
        // Entity.
        // TODO: Test that the input state is not modified here
        State newState = new State();

        double accel = Acceleration.sum(accels);

        a = evaluate(state, t, 0.0, new Derivative(), accel);
        b = evaluate(state, t, dt * 0.5, a, accel);
        c = evaluate(state, t, dt * 0.5, b, accel);
        d = evaluate(state, t, dt, c, accel);

        double dpdt = 1.0/6.0 * (a.dp + 2.0 * (b.dp + c.dp) + d.dp);
        double dvdt = 1.0/6.0 * (a.dv + 2.0 * (b.dv + c.dv) + d.dv);

        newState.p = state.p + dpdt * dt;
        newState.v = state.v + dvdt * dt;

        return newState;
    }
}
