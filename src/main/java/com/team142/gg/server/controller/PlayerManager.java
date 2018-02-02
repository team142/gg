/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.mappable.meta.DirectionTypes;
import com.team142.gg.server.model.messages.incoming.MessageKeyDown;
import com.team142.gg.server.model.messages.incoming.MessageKeyUp;

/**
 *
 * @author just1689
 */
public class PlayerManager {

    public static void handle(MessageKeyDown message) {
        PlayerManager.keyDown(Repository.PLAYERS_ON_SERVER.get(message.getFrom()), message.getKey());
    }

    public static void handle(MessageKeyUp message) {
        PlayerManager.keyUp(Repository.PLAYERS_ON_SERVER.get(message.getFrom()), message.getKey());
    }

    public static void keyUp(Player player, String key) {
        player.getTANK().setDirection(0);

    }

    public static void keyDown(Player player, String key) {

        char c = key.charAt(0);
        if (c >= '0' && c <= '9') {
            PowerManager.handle(player, key);
            return;
        }

        switch (c) {
            case 'A':
                player.getTANK().setRotation(player.getTANK().getRotation().subtract(DirectionTypes.ONE_TICK_ROTATE));
                if (player.getTANK().getRotation().compareTo(DirectionTypes.DIR0) < 0) {
                    player.getTANK().setRotation(DirectionTypes.DIR7);
                }
                return;
            case 'W':
                player.getTANK().setDirection(1);
                return;
            case 'D':
                player.getTANK().setRotation(player.getTANK().getRotation().add(DirectionTypes.ONE_TICK_ROTATE));
                if (player.getTANK().getRotation().compareTo(DirectionTypes.DIR8) >= 0) {
                    player.getTANK().setRotation(DirectionTypes.DIR0);
                }
                return;
            case 'S':
                player.getTANK().setDirection(-1);
                return;
            case ' ':
                PowerManager.handle(player, "1");
                return;
            default:
                break;
        }

        player.getTANK().setDirection(0);

    }

}
