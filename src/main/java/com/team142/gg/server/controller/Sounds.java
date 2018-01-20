/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.messages.base.SoundType;
import com.team142.gg.server.model.messages.outgoing.other.MessagePlaySound;

/**
 *
 * @author just1689
 */
public class Sounds {

    public static void sendSound(Game game, String sound) {

    }

    public static void sendShoot(Game game) {
        PostOffice.sendPlayersAMessage(game, new MessagePlaySound(SoundType.PEW));

    }

    public static void sendDing(Game game) {
        PostOffice.sendPlayersAMessage(game, new MessagePlaySound(SoundType.DING));

    }

    public static void sendExplode(Game game) {
        PostOffice.sendPlayersAMessage(game, new MessagePlaySound(SoundType.EXPLODE));

    }

    public static void sendSpawn(Game game) {
        PostOffice.sendPlayersAMessage(game, new MessagePlaySound(SoundType.SHHHA));

    }

}
