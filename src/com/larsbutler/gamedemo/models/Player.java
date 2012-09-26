package com.larsbutler.gamedemo.models;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity {

    public static double MAX_XVEL_PER_UPDATE = 500;
    public static double CEL_PER_SECOND = 2000;  // accel/decel per second

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

    public void render(Graphics g, double alpha) {
        g.setColor(Color.orange);
        g.fillRect(
            (int)Math.round(getX()), (int)Math.round(getY()),
            getWidth(), getHeight());
    }

}
