package com.larsbutler.gamedemo.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.larsbutler.gamedemo.core.GameState;
import com.larsbutler.gamedemo.core.Kernel;
import com.larsbutler.gamedemo.models.Level;
import com.larsbutler.gamedemo.models.Player;

public class DisplayProxy {

    public static final int MAX_FRAMES_PER_MEASURE = 200;

    private double alpha;

    private long measureStart;
    private long measureEnd;
    private double fps;
    private int frameCounter;

    public DisplayProxy() {
        initOpenGL();
    }

    private void initOpenGL() {
        // Create the window
        try {
            Display.setDisplayMode(new DisplayMode(816, 624));
            Display.create();
        }
        catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Initialize OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 816, 0, 624, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        // Synchronize the render rate with the display's
        // refresh rate.
        // A typical framerate resulting from this would be
        // 60 FPS, or 60Hz.
        Display.setVSyncEnabled(true);
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * Call on each render update to keep track of FPS rate.
     */
    public void measureFps() {
        frameCounter++;
        if (frameCounter >= MAX_FRAMES_PER_MEASURE) {
            measureEnd = System.nanoTime();
            double totalSeconds = (double)(measureEnd - measureStart) / Kernel.NANOS_PER_SECOND;
            fps = frameCounter / totalSeconds;
            frameCounter = 0;
            measureStart = measureEnd;
        }
    }

    public double getFps() {
        return fps;
    }

    public void render(double alpha) {
        Kernel k = Kernel.instance();
        GameState gs = k.gameState();

        gs.getLevel().render(alpha);
        gs.getPlayer().render(alpha);

        // draw HUD
        if (k.isPaused()) {
            // draw a translucent overlay to indicate
            // the paused state
            GL11.glColor4f(0.4f, 0.2f, 0.2f, 0.5f);
            {
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(0, 0);
                GL11.glVertex2d(0, 624);
                GL11.glVertex2d(816, 624);
                GL11.glVertex2d(816, 0);
                GL11.glEnd();
            }
        }
//        g.drawString(String.format("FPS: %d", Math.round(fps)), 5, 25);
    }

}