/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.controller.PowerManager;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.messages.outgoing.other.MessageIntelChange;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Power07Intel extends Power {

    private static final int INITIAL_COOLDOWN = 5000;

    public Power07Intel(Player player) {
        super(7, player, 0, INITIAL_COOLDOWN, 1);
    }

    @Override
    public void execute() {
        MessageIntelChange message = new MessageIntelChange(true);
        MessageManager.sendPlayerAMessage(getPlayer().getId(), message);
        PowerManager.sendCooldown(getPlayer().getId(), this, 7);

        new Thread(() -> {
            try {
                Thread.sleep(getRefreshTime());
                MessageIntelChange message1 = new MessageIntelChange(false);
                MessageManager.sendPlayerAMessage(getPlayer().getId(), message1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Power07Intel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }
}
