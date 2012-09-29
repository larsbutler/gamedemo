package com.larsbutler.gamedemo.core;

import org.lwjgl.input.Keyboard;
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

    public static final boolean KEY_DOWN = true;

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
            handleInput();

            long newTime = System.nanoTime();
            
//            long pauseTime = pause();
            // Draw a normal frame
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

            long frameTime;
            if (isPaused()) {
                drawPauseScreen();
                frameTime = 0;
            }
            else {
                // produced frame time (for simulation consumption)
                frameTime = newTime - currentTime;
            }

            // Actually update the display to draw this frame.
            // If we don't do this, nothing gets drawn.
            Display.update();

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
        }

        Display.destroy();
        System.exit(0);
    }

    private void drawPauseScreen() {
        GL11.glColor4f(0.2f, 0.1f, 0.1f, 0.5f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(0, 0);
            GL11.glVertex2d(0, 624);
            GL11.glVertex2d(816, 624);
            GL11.glVertex2d(816, 0);
            GL11.glEnd();
        }
    }

    public void handleInput() {
        int keycode;
        char keychar;
        while (Keyboard.next()) {
            keycode = Keyboard.getEventKey();
            keychar = Keyboard.getEventCharacter();

            if (Keyboard.getEventKeyState() == KEY_DOWN) {
                keyPressed(keycode, keychar);
            }
            else {
                keyReleased(keycode, keychar);
            }
        }
    }

    /**
     * We pass the code _and_ the character, in the case of keys like
     * ~ and ` (which both have a keycode of 0).
     */
    public void keyPressed(int keycode, char keychar) {
        switch (keycode) {
            case Keyboard.KEY_P:
                // Toggle the `pause` state.
                setPaused(!isPaused());
                System.out.println("paused? " + isPaused());
                break;
            default:
                if (!isPaused()) {
                    gameState.keyPressed(keycode, keychar);
                }
                break;
        }
    }

    /**
     * We pass the code _and_ the character, in the case of keys like
     * ~ and ` (which both have a keycode of 0).
     */
    public void keyReleased(int keycode, char keychar) {
        gameState.keyReleased(keycode, keychar);
    }

}
