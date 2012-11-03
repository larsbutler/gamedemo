package com.larsbutler.gamedemo.models;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.larsbutler.gamedemo.core.Kernel;

public class Level {

    private final int rows;
    private final int cols;
    private final int height;  // px
    private final int width;  // px
    private int [][] tiles;

    public Level(int rows, int cols) {
       this.rows = rows;
       this.cols = cols;
       this.height = rows * Kernel.TILE_SIZE;
       this.width = cols * Kernel.TILE_SIZE;
       tiles = new int[this.rows][this.cols];
    }

    public static Level sampleLevel() {
        Level lvl = new Level(13, 17);

        // ceiling
        for (int i = 0; i < 17; i++) {
            lvl.set(0, i, 1);
        }
        // floor
        for (int i = 0; i < 17; i++) {
            lvl.set(12, i, 1);
        }
        // left wall
        for (int i = 1; i < 12; i++) {
            lvl.set(i, 0, 1);
        }
        // right wall
        for (int i = 1; i < 12; i++) {
            lvl.set(i, 16, 1);
        }

        // floating platform
        lvl.set(6, 2, 1);
        lvl.set(6, 3, 1);
        lvl.set(6, 4, 1);

        // another floating platform
        lvl.set(9, 5, 1);
        lvl.set(9, 6, 1);
        lvl.set(9, 7, 1);
        return lvl;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int get(int row, int col) {
        return tiles[row][col];
    }

    public void set(int row, int col, int value) {
        tiles[row][col] = value;
    }

    public void render(double alpha) {
        GL11.glColor3f(0.3f, 0.3f, 0.3f);
        // draw the tiles
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // If this tile has a value of 1, draw it
                // Otherwise, ignore it
                if (tiles[row][col] == 1) {
                    {
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glVertex2d(col * Kernel.TILE_SIZE, row * Kernel.TILE_SIZE);
                        GL11.glVertex2d(col * Kernel.TILE_SIZE, row * Kernel.TILE_SIZE + Kernel.TILE_SIZE);
                        GL11.glVertex2d(col * Kernel.TILE_SIZE + Kernel.TILE_SIZE, row * Kernel.TILE_SIZE + Kernel.TILE_SIZE);
                        GL11.glVertex2d(col * Kernel.TILE_SIZE + Kernel.TILE_SIZE, row * Kernel.TILE_SIZE);
                        GL11.glRectd(
                            col * Kernel.TILE_SIZE, row * Kernel.TILE_SIZE,
                            col * Kernel.TILE_SIZE + Kernel.TILE_SIZE, row * Kernel.TILE_SIZE + Kernel.TILE_SIZE);
                        GL11.glEnd();
                    }
                }
            }
        }
    }

    public List<Rectangle2D> tileHitBoxes() {
        List<Rectangle2D> boxes = new ArrayList<Rectangle2D>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (tiles[row][col] == 1) {
                    boxes.add(new Rectangle2D.Double(
                        col * Kernel.TILE_SIZE, row * Kernel.TILE_SIZE,
                        Kernel.TILE_SIZE, Kernel.TILE_SIZE));
                }
            }
        }
        return boxes;
    }
}
