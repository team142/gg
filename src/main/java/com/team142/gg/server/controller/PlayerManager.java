/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.mappable.meta.DirectionTypes;

/**
 *
 * @author just1689
 */
public class PlayerManager {

    public static void keyUp(Player player, String key) {
        player.getTANK().setDirection(0);

    }

    /**
     *
     * @param player
     * @param key
     * @return a boolean is returned indicating if the player chooses to shoot
     */
    public static boolean keyDown(Player player, String key) {
        switch (key) {
            case "A":
                player.getTANK().setRotation(player.getTANK().getRotation().subtract(DirectionTypes.ONE_TICK_ROTATE));
                if (player.getTANK().getRotation().compareTo(DirectionTypes.DIR0) < 0) {
                    player.getTANK().setRotation(DirectionTypes.DIR7);
                }
                return false;
            case "W":
                player.getTANK().setDirection(1);
                return false;
            case "D":
                player.getTANK().setRotation(player.getTANK().getRotation().add(DirectionTypes.ONE_TICK_ROTATE));
                if (player.getTANK().getRotation().compareTo(DirectionTypes.DIR8) >= 0) {
                    player.getTANK().setRotation(DirectionTypes.DIR0);
                }
                return false;
            case "S":
                player.getTANK().setDirection(-1);
                return false;
            case " ":
                return true;
            default:
                break;
        }

        player.getTANK().setDirection(0);
        return false;

    }

}
