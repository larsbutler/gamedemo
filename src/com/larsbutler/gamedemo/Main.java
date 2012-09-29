package com.larsbutler.gamedemo;

import com.larsbutler.gamedemo.core.GameState;
import com.larsbutler.gamedemo.core.Kernel;
import com.larsbutler.gamedemo.models.Level;
import com.larsbutler.gamedemo.models.Player;

public class Main {

    public static void main (String [] args) {
        Kernel kernel = Kernel.instance();
        GameState gs = kernel.gameState();

        // init game entities
        Level level = Level.sampleLevel();
        gs.setLevel(level);
        Player player = new Player(
            100, 200, Kernel.TILE_SIZE, Kernel.TILE_SIZE * 2);
        gs.setPlayer(player);

        Thread mainThread = new Thread(kernel);
        mainThread.start();
    }
}
