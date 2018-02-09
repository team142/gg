/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.controller.runnable.powers.Power;
import com.team142.gg.server.model.messages.outgoing.other.MessageCooldown;

/**
 *
 * @author just1689
 */
public class PowerManager {

    private static final int CLIENT_COOLDOWN_TICK_MS = 20;

    public static void handle(Player player, String key) {
        Power power = player.getPowers().get(key);
        if (power != null) {
            power.run();
        }
    }

    public static void sendCooldown(String playerId, Power power, int num) {
        long ms = power.getRefreshTime() / CLIENT_COOLDOWN_TICK_MS;
        MessageCooldown message = new MessageCooldown((int) ms, num);
        MessageManager.sendPlayerAMessage(playerId, message);

    }

}
