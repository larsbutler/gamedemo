package com.larsbutler.gamedemo.math;

import java.awt.geom.Rectangle2D;

import com.larsbutler.gamedemo.models.Entity;

public class Collision {

    /**
     * Given an entity and the rectangle representing a _potential_ collision,
     * calculate a correction value.
     *
     * If a collision has occurred (on the given axis), the position correction
     * should be just enough to move the entity out of the collision.
     *
     * If there is no collision, the correction will be 0.
     */
    public static double getYCorrection(Entity e, Rectangle2D hitBox) {
        State yState = e.getYState();
        Rectangle2D pathBox = getYPathBox(e);
        Rectangle2D inter = pathBox.createIntersection(hitBox);
        double correction = 0.0;

        if (!inter.isEmpty()) {
            if (yState.v > 0.0) {
                // Falling
                correction = hitBox.getMinY() - pathBox.getMaxY();
            }
            else if (yState.v < 0.0) {
                // Rising
                correction = hitBox.getMaxY() - pathBox.getMinY();
            }
        }
        return correction;
    }

    /**
     * Given an entity and the rectangle representing a _potential_ collision,
     * calculate a correction value.
     *
     * If a collision has occurred (on the given axis), the position correction
     * should be just enough to move the entity out of the collision.
     *
     * If there is no collision, the correction will be 0.
     */
    public static double getXCorrection(Entity e, Rectangle2D hitBox) {
        State xState = e.getXState();
        Rectangle2D pathBox = getXPathBox(e);
        Rectangle2D inter = pathBox.createIntersection(hitBox);
        double correction = 0.0;

        if (!inter.isEmpty()) {
            if (xState.v > 0.0) {
                // Moving right
                correction = hitBox.getMinX() - pathBox.getMaxX();
            }
            else if (xState.v < 0.0) {
                // Moving left
                correction = hitBox.getMaxX() - pathBox.getMinX();
            }
        }
        return correction;
    }

    /**
     * Get a rectangle representing the path the entity has taken
     * (on the Y axis) between its previous state and current state.
     */
    public static Rectangle2D getYPathBox(Entity e) {
        double prevMin, prevMax;
        double curMin, curMax;
        double top, bottom, left, right;

        prevMin = e.getPrevYState().p;
        prevMax = e.getPrevYState().p + e.getHeight();

        curMin = e.getY();
        curMax = e.getY() + e.getHeight();

        top = MathUtil.min(prevMin, prevMax, curMin, curMax);
        bottom = MathUtil.max(prevMin, prevMax, curMin, curMax);

        left = e.getX();
        right = e.getX() + e.getWidth();

        return new Rectangle2D.Double(
            left, top, right - left, bottom - top);
    }

    /**
     * Get a rectangle representing the path the entity has taken
     * (on the X axis) between its previous state and current state.
     */
    public static Rectangle2D getXPathBox(Entity e) {
        double prevMin, prevMax;
        double curMin, curMax;
        double top, bottom, left, right;

        prevMin = e.getPrevXState().p;
        prevMax = e.getPrevXState().p + e.getWidth();

        curMin = e.getX();
        curMax = e.getX() + e.getWidth();

        top = e.getY();
        bottom = e.getY() + e.getHeight();

        left = MathUtil.min(prevMin, prevMax, curMin, curMax);
        right = MathUtil.max(prevMin, prevMax, curMin, curMax);

        return new Rectangle2D.Double(
            left, top, right - left, bottom - top);
    }
}
