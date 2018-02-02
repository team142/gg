/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.controller.runnable.powers.Power;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class PowerManager {

    private static final Logger LOG = Logger.getLogger(PowerManager.class.getName());

    public static void handle(Player player, String key) {
        Power power = player.getPowers().get(key);
        if (power != null) {
            power.run();
        }
        LOG.log(Level.INFO, "POWER TIME: " + player.getName() + ", " + key);

    }

}
