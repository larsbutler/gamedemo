package com.larsbutler.gamedemo.models;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import com.larsbutler.gamedemo.math.State;

public abstract class Entity {

    protected State xState;
    protected State yState;
    protected State prevXState;
    protected State prevYState;

    protected int width;
    protected int height;

    protected boolean canJump;

    public Entity(double x, double y, int width, int height) {
        xState = new State();
        xState.p = x;
        yState = new State();
        yState.p = y;

        prevXState = new State();
        prevYState = new State();

        this.width = width;
        this.height = height;
    }

    public double getX() {
        return xState.p;
    }

    public void setX(double newX) {
        xState.p = newX;
    }

    public double getY() {
        return yState.p;
    }

    public void setY(double newY) {
        yState.p = newY;
    }

    public State getXState() {
        return xState;
    }

    public void setXState(State x) {
        this.xState = x;
    }

    public State getYState() {
        return yState;
    }

    public void setYState(State y) {
        this.yState = y;
    }

    public State getPrevXState() {
        return prevXState;
    }

    public void setPrevXState(State prevX) {
        this.prevXState = prevX;
    }

    public State getPrevYState() {
        return prevYState;
    }

    public void setPrevYState(State prevY) {
        this.prevYState = prevY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean canJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public Rectangle2D rect() {
        return new Rectangle2D.Double(xState.p, yState.p, (double)width, (double)height);
    }

    public Rectangle2D prevRect() {
        return new Rectangle2D.Double(prevXState.p, prevYState.p, (double)width, (double)height);
    }

    public double getMaxAbsXVel() {
        return Double.POSITIVE_INFINITY;
    }

    public double getMaxAbsYVel() {
        return Double.POSITIVE_INFINITY;
    }

    public abstract void jump();

    public abstract void render(Graphics g, double alpha);
}
