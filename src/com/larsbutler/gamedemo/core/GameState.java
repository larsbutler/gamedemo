package com.larsbutler.gamedemo.core;

import com.larsbutler.gamedemo.models.Level;
import com.larsbutler.gamedemo.models.Player;

public class GameState {

    private Level level;
    private Player player;

    /**
     * Do all physics and entity updates here.
     * 
     * @param timeStepNanos
     *            Time delta (in ns) to simulate physics. For example, if
     *            timeStepNanos == 1/60 sec, we do physics updates for 1/60 sec.
     */
    public void update(long prevTimeStep, long timeStepNanos) {
        for (long i = 0; i < 10000000; i++) {
            
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
