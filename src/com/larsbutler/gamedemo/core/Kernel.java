package com.larsbutler.gamedemo.core;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.larsbutler.gamedemo.graphics.DisplayProxy;


public class Kernel implements Runnable {

    public static final long NANOS_PER_SECOND = 1000000000;

    /**
     * Nanoseconds per update (independent from render rate).
     */
    public static final long UPDATE_RATE_NANOS = NANOS_PER_SECOND / 60;  // 100 updates/sec
    
    /**
     * When the game is paused, sleep for this long before checking the pause
     * state again.
     */
    public static final int PAUSE_POLLING_MS = 100;  // 1/10th of a second

    public static final int TILE_SIZE = 48;  // px

    private static final Kernel instance = new Kernel();
    private final GameState gameState = new GameState();
    private static DisplayProxy dispProxy = null;

    private boolean paused;

    private Kernel() {
        
    }

    public static Kernel instance() {
        return instance;
    }

    public GameState gameState() {
        return gameState;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * The main game loop.
     *
     * Heavily inspired by an article by Glenn Fiedler:
     * http://gafferongames.com/game-physics/fix-your-timestep/
     * Design copied (and modified) with permission.
     */
    public void run() {
        dispProxy = new DisplayProxy();

        long t = 0;
        long dt = UPDATE_RATE_NANOS;
        long currentTime = System.nanoTime();
        long accum = 0;

        while (!Display.isCloseRequested()) {
            long newTime = System.nanoTime();
            // produced frame time (for simulation consumption)
            long pauseTime = pause();
            long frameTime = newTime - currentTime - pauseTime;
            // TODO: add spiral of death protection
            currentTime = newTime;

            accum += frameTime;

            while (accum >= dt) {
                // consume time
                gameState.update(t, dt);

                // transfer time from the accumulator
                // to the total (t)
                t += dt;
                accum -= dt;
            }

            // blending factor, in case we're part-way between
            // physics states
            double alpha = accum / (double)dt;
            // Clear the screen and depth buffer
            // if you don't do this, you can accidentally draw
            // random stuff from video memory.. which is cool,
            // but not very useful =)
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            dispProxy.render(alpha);
            dispProxy.measureFps();

            Display.update();
        }

        Display.destroy();
        System.exit(0);
    }

    /**
     * If the game is paused, block until it is unpaused and return
     * the duration of pause time (in nanoseconds).
     * 
     * If the game is not paused, simply return 0.
     */
    public long pause() {
        if (!isPaused()) {
            return 0;
        }
        else {
            long startPauseTime = System.nanoTime();
            do {
                try {
                    Thread.sleep(PAUSE_POLLING_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (isPaused());
            return System.nanoTime() - startPauseTime;
        }
    }

}
