/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.controller.runnable.powers.Power;

/**
 *
 * @author just1689
 */
public class PowerManager {

    public static void handle(Player player, String key) {
        Power power = player.getPowers().get(key);
        if (power != null) {
            power.run();
        }
    }

}
