/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.messages.base.SoundType;
import com.team142.gg.server.model.messages.outgoing.other.MessagePlaySound;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
public class SoundManager {

    private final String GAME_ID;

    public final Executor SOUND_MSG_THREAD_POOL = Executors.newFixedThreadPool(4, (Runnable r) -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public void sendSound(SoundType type) {
        SOUND_MSG_THREAD_POOL.execute(() -> MessageManager.sendPlayersAMessage(Repository.GAMES_ON_SERVER.get(GAME_ID), new MessagePlaySound(type)));
    }

    public void sendShoot() {
        sendSound(SoundType.PEW);

    }

    public void sendDing() {
        sendSound(SoundType.DING);

    }

    public void sendExplode() {
        sendSound(SoundType.EXPLODE);

    }

    public void sendSpawn() {
        sendSound(SoundType.SHHHA);

    }

    public void sendDoubleKill() {
        sendSound(SoundType.X2);
    }

    public void sendTripleKill() {
        sendSound(SoundType.X3);
    }

    public void sendQuadKill() {
        sendSound(SoundType.X4);
    }

    public void sendPentaKill() {
        sendSound(SoundType.X5);
    }

}
