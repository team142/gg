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

    public static void keyDown(Player player, String key) {
        switch (key) {
            case "A":
                player.getTANK().setRotation(player.getTANK().getRotation().subtract(DirectionTypes.ONE_TICK_ROTATE));
                if (player.getTANK().getRotation().compareTo(DirectionTypes.DIR0) < 0) {
                    player.getTANK().setRotation(DirectionTypes.DIR7);
                }
                return;
            case "W":
                player.getTANK().setDirection(1);
                return;
            case "D":
                player.getTANK().setRotation(player.getTANK().getRotation().add(DirectionTypes.ONE_TICK_ROTATE));
                if (player.getTANK().getRotation().compareTo(DirectionTypes.DIR8) >= 0) {
                    player.getTANK().setRotation(DirectionTypes.DIR0);
                }
                return;
            case "S":
                player.getTANK().setDirection(-1);
                return;
            case " ":
                player.attemptShoot();
                return;
            default:
                break;
        }

        player.getTANK().setDirection(0);
        return;

    }

}
