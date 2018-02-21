/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.incoming;

import com.team142.gg.server.controller.PlayerManager;
import com.team142.gg.server.controller.PowerManager;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.MessageKey;

/**
 *
 * @author just1689
 */
public class MessageKeyDown extends MessageKey implements Runnable {

    public MessageKeyDown() {
        super(ConversationType.P_KD);
    }

    @Override
    public void run() {
        if (getKey().toUpperCase().equals("P")) {
            Player player = Repository.PLAYERS_ON_SERVER.get(getFrom());
            PowerManager.givePlayerRandomPower(player);
            return;
        }
        PlayerManager.handle(this);
    }

}
