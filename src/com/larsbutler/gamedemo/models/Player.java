package com.larsbutler.gamedemo.models;

import java.awt.Color;
import java.awt.Graphics;

import com.larsbutler.gamedemo.math.MathUtil;

public class Player extends Entity {

    public static double MAX_XVEL_PER_UPDATE = 500;
    public static double CEL_PER_SECOND = 2000;  // accel/decel per second
    /**
     * Jump y-axis displacement.
     */
    public static double JUMP_HEIGHT = 200.0;

    private boolean left;
    private boolean right;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void jump() {
        if (canJump) {
            this.getYState().v = -MathUtil.u(-MathUtil.GRAVITY, JUMP_HEIGHT, 0.0);
            canJump = false;
        }
    }

    @Override
    public double getMaxAbsXVel() {
        return MAX_XVEL_PER_UPDATE;
    }

    public void render(Graphics g, double alpha) {
        g.setColor(Color.orange);
        g.fillRect(
            (int)Math.round(getX()), (int)Math.round(getY()),
            getWidth(), getHeight());
    }

}
