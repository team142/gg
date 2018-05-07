/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.base;

import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.Server;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author just1689
 */
public abstract class AbstractTickerWorker implements Runnable {

    private static final Logger LOG = Logger.getLogger(GameManager.class.getName());

    @Getter
    private final String PLAYER_ID;

    @Getter
    private final String GAME_ID;

    @Getter
    private final AtomicBoolean RUNNING = new AtomicBoolean(true);
    private long nextSleepTimeMs;

    @Setter
    @Getter
    private int TICK_MS;

    public AbstractTickerWorker(String playerId, String gameId) {
        this.GAME_ID = gameId;
        this.PLAYER_ID = playerId;
        this.TICK_MS = Server.TICK_MS;
    }

    public void stopNow() {
        RUNNING.set(false);
    }

    @Override
    public void run() {
        while (RUNNING.get()) {
            long startTime = System.currentTimeMillis();

            doTick();

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            if (duration < TICK_MS) {
                nextSleepTimeMs = TICK_MS - duration;
                nap();
            } else if (duration > TICK_MS) {
                LOG.log(Level.WARNING, "Game ticker took long! Time taken: {0} ms", duration);
            }
        }

    }

    public abstract void doTick(); // The work happens here

    private void nap() {
        try {
            Thread.sleep(nextSleepTimeMs);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }

}
