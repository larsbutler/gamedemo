package com.larsbutler.gamedemo;

import com.larsbutler.gamedemo.core.GameState;
import com.larsbutler.gamedemo.core.Kernel;
import com.larsbutler.gamedemo.graphics.GameCanvas;
import com.larsbutler.gamedemo.graphics.Display;
import com.larsbutler.gamedemo.models.Level;
import com.larsbutler.gamedemo.models.Player;

public class Main {

    public static void main (String [] args) {
        Kernel kernel = Kernel.instance();
        GameState gs = kernel.gameState();

        // init game entities
        Level level = Level.sampleLevel();
        gs.setLevel(level);
        Player player = new Player();
        gs.setPlayer(player);

        // init graphics
        Display disp = new Display();
        disp.addKeyListener(disp);

        kernel.setDisplay(disp);
        Thread mainThread = new Thread(kernel);
        mainThread.start();
    }
}
