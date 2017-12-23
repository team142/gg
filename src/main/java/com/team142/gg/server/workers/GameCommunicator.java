/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.workers;

import com.team142.gg.server.model.Game;

/**
 *
 * @author just1689
 */
public class GameCommunicator implements Runnable {

    private final Game GAME;

    public GameCommunicator(Game GAME) {
        this.GAME = GAME;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
