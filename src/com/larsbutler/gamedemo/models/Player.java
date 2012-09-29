package com.larsbutler.gamedemo.models;

import org.lwjgl.opengl.GL11;

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
            this.getYState().v = MathUtil.u(MathUtil.GRAVITY, JUMP_HEIGHT, 0.0);
            canJump = false;
        }
    }

    @Override
    public double getMaxAbsXVel() {
        return MAX_XVEL_PER_UPDATE;
    }

    public void render(double alpha) {
        double renderX, renderY;
        renderX = MathUtil.getRenderPosition(prevXState, xState, alpha);
        renderY = MathUtil.getRenderPosition(prevYState, yState, alpha);

        GL11.glColor3f(1.0f, 0.8f, 0.0f);
        {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(renderX, renderY);
            GL11.glVertex2d(renderX, renderY + getHeight());
            GL11.glVertex2d(renderX + getWidth(), renderY + getHeight());
            GL11.glVertex2d(renderX + getWidth(), renderY);
            GL11.glEnd();
        }
    }

}
