/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class GameTicker implements Runnable {

    private final Game GAME;
    private final AtomicBoolean RUNNING = new AtomicBoolean(true);

    public GameTicker(Game GAME) {
        this.GAME = GAME;
    }

    @Override
    public void run() {
        while (RUNNING.get()) {
            long startTime = System.currentTimeMillis();

            //Move things
            moveThings();

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            if (duration > 50) {
                Logger.getLogger(Referee.class.getName()).log(Level.WARNING, "Game ticker took: {0} ms", duration);
            }
        }
    }

    private void moveThings() {
        //TODO: implement
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameTicker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
