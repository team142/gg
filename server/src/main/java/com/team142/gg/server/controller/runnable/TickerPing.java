/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author just1689
 */
@AllArgsConstructor
public class TickerPing implements Runnable {

    private final List<Player> PLAYERS;
    private final int SLEEP_SECONDS = 5;

    @Override
    public void run() {
        while (true) {
            try {
                PLAYERS.forEach((player) -> player.getTickerComms().ping());
                Thread.sleep(SLEEP_SECONDS * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
