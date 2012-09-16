package com.larsbutler.gamedemo.graphics;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.larsbutler.gamedemo.core.Kernel;

public class Display extends JFrame implements KeyListener {

    public static final Dimension WINDOW_SIZE = new Dimension(816, 624);

    private GameCanvas gc;

    public Display() {
        this.gc = new GameCanvas();
        getContentPane().add(this.gc);
        this.gc.setPreferredSize(WINDOW_SIZE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Game Demo");
        this.pack();
        setResizable(false);
        setPreferredSize(WINDOW_SIZE);
        setVisible(true);
    }

    public GameCanvas getGameCanvas() {
        return gc;
    }

    public void keyPressed(KeyEvent e) {
        Kernel.instance().keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        Kernel.instance().keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {}
}
