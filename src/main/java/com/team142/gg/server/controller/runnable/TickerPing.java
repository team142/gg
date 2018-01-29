/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
public class TickerPing implements Runnable {

    private final List<Player> PLAYERS;

    @Override
    public void run() {
        while (true) {
            try {
                PLAYERS.forEach((player) -> player.getTickerComms().ping());
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
