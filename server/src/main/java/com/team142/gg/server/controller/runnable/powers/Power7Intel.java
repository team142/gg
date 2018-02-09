/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.messages.outgoing.other.MessageIntelChange;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Power7Intel extends Power {

    private long onTimeMs = 10000;

    public Power7Intel(Player player, long refreshTime) {
        super(player, 0, refreshTime);
    }

    @Override
    public void execute() {
        MessageIntelChange message = new MessageIntelChange(true);
        MessageManager.sendPlayerAMessage(getPlayer().getId(), message);
        new Thread(() -> {
            try {
                Thread.sleep(onTimeMs);
                MessageIntelChange message1 = new MessageIntelChange(false);
                MessageManager.sendPlayerAMessage(getPlayer().getId(), message1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Power7Intel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }

}
