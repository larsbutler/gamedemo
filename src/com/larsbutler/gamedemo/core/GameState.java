package com.larsbutler.gamedemo.core;

import java.awt.geom.Rectangle2D;
import java.util.List;

import com.larsbutler.gamedemo.math.Acceleration;
import com.larsbutler.gamedemo.math.MathUtil;
import com.larsbutler.gamedemo.math.RK4;
import com.larsbutler.gamedemo.math.State;
import com.larsbutler.gamedemo.models.Entity;
import com.larsbutler.gamedemo.models.Level;
import com.larsbutler.gamedemo.models.Player;

public class GameState {

    private Level level;
    private Player player;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Do all physics and entity updates here.
     * 
     * @param timeStepNanos
     *            Time delta (in ns) to simulate physics. For example, if
     *            timeStepNanos == 1/60 sec, we do physics updates for 1/60 sec.
     */
    public void update(long prevTimeStep, long timeStepNanos) {
        double t = (double)prevTimeStep / Kernel.NANOS_PER_SECOND;
        double dt = (double)timeStepNanos / Kernel.NANOS_PER_SECOND;
        move(player, t, dt);
    }

    public void move(Entity e, double t, double dt) {
        State currentXState = e.getXState();
        State currentYState = e.getYState();
        e.setPrevXState(currentXState);
        e.setPrevYState(currentYState);

        // All of the solid tiles we can collide with:
        List<Rectangle2D> levelHitBoxes = level.tileHitBoxes();
        double correction = 0.0;
        // Move one axis at a time, and check for collisions after each.
        e.setXState(
            RK4.integrate(currentXState, t, dt, Acceleration.EMPTY));
        for (Rectangle2D box : levelHitBoxes) {
            correction = getCollisionCorrection(e, box, MathUtil.XAXIS);
            e.setX(e.getX() + correction);
        }

        e.setYState(
            RK4.integrate(currentYState, t, dt, Acceleration.GRAVITY_ONLY));
        for (Rectangle2D box : levelHitBoxes) {
            correction = getCollisionCorrection(e, box, MathUtil.YAXIS);
            e.setY(e.getY() + correction);
        }
    }

    public static double getCollisionCorrection(Entity e, Rectangle2D hitBox, int axis) {
        // TODO: This is an incredibly over-simplified collision
        // detection/response procedure. This will break if the entity
        // is moving very fast.
        // It's possible for the entity to pass right through walls,
        // floors, etc.
        double correction = 0.0;
        Rectangle2D intersection = e.rect().createIntersection(hitBox);
        if (!intersection.isEmpty()) {
            // there is a collision
            if (axis == MathUtil.XAXIS) {
                correction = intersection.getWidth();
                // figure out the direction--along the given axis--to correct
                if (e.getXState().v > 0) {
                    // entity is moving to the right
                    // correct left
                    correction *= -1;
                }
            }
            else if (axis == MathUtil.YAXIS) {
                correction = intersection.getHeight();
                // NOTE: In java2d, the origin of the graphics coord system
                // is in the upper left. Y increases downward
                if (e.getYState().v > 0) {
                    // entity is moving down
                    // correct up
                    correction *= -1;
                }
            }
        }
        return correction;
    }

}
