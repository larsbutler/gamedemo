package com.larsbutler.gamedemo.models;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity {

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public void render(Graphics g, double alpha) {
        g.setColor(Color.orange);
        g.fillRect(
            (int)Math.round(getX()), (int)Math.round(getY()),
            getWidth(), getHeight());
    }

}
