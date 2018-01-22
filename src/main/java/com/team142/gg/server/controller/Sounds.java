/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.messages.base.SoundType;
import com.team142.gg.server.model.messages.outgoing.other.MessagePlaySound;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author just1689
 */
public class Sounds {

    public static final Executor SOUND_MSG_THREAD_POOL = Executors.newFixedThreadPool(10, (Runnable r) -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public static void sendSound(Game game, SoundType type) {
        SOUND_MSG_THREAD_POOL.execute(() -> PostOffice.sendPlayersAMessage(game, new MessagePlaySound(type)));
    }

    public static void sendShoot(Game game) {
        sendSound(game, SoundType.PEW);

    }

    public static void sendDing(Game game) {
        sendSound(game, SoundType.DING);

    }

    public static void sendExplode(Game game) {
        sendSound(game, SoundType.EXPLODE);

    }

    public static void sendSpawn(Game game) {
        sendSound(game, SoundType.SHHHA);

    }

}
