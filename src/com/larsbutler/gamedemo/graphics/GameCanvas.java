package com.larsbutler.gamedemo.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import com.larsbutler.gamedemo.core.GameState;
import com.larsbutler.gamedemo.core.Kernel;
import com.larsbutler.gamedemo.models.Level;
import com.larsbutler.gamedemo.models.Player;

public class GameCanvas extends Canvas {

    public static final int MAX_FRAMES_PER_MEASURE = 200;

    private double alpha;

    private long measureStart;
    private long measureEnd;
    private double fps;
    private int frameCounter;

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

    @Override
    public void paint(Graphics g) {
        Kernel k = Kernel.instance();
        Display disp = k.getDisplay();
        GameState gs = k.gameState();

        Level level = gs.getLevel();
        level.render(g, alpha);

        // TODO: render the player
        Player p = gs.getPlayer();
        p.render(g, alpha);

        // draw HUD
        g.setColor(Color.cyan);
        if (k.isPaused()) {
            // Draw a "Paused" message, more or less centered
            // on the display
            g.drawString(
                "Paused",
                disp.getWidth() / 2 - 25,
                disp.getHeight() / 2 - 10);
        }
        g.drawString(String.format("FPS: %d", Math.round(fps)), 5, 25);
    }

}
