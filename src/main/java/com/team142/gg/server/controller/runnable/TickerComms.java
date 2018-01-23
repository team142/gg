/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageShareThingsDynamic;
import com.team142.gg.server.controller.runnable.base.AbstractTickerWorker;

/**
 *
 * @author just1689
 */
public class TickerComms extends AbstractTickerWorker {

    private long lastPing = 0;

    public TickerComms(String playerId, String gameId) {
        super(playerId, gameId);
    }

    @Override
    public void doTick() {
        checkPing();
        MessageManager.sendPlayerAMessage(getPLAYER_ID(), getDynamicThingsMessage());

    }

    private MessageShareThingsDynamic getDynamicThingsMessage() {
        return new MessageShareThingsDynamic(Repository.GAMES_ON_SERVER.get(getGAME_ID()));
    }

    private void checkPing() {
        if (lastPing == 0) {
            ping();
        }

    }

    public void ping() {
        MessageManager.sendPlayerAMessage(Repository.SESSIONS_ON_SERVER.get(getPLAYER_ID()), "0");
        lastPing = System.currentTimeMillis();

    }

    public void pong() {
        long lag = System.currentTimeMillis() - lastPing;

        lag = lag / 2;
        if (lag < 20) {
            setTICK_MS(20);
        } else if (lag < 30) {
            setTICK_MS(30);
        } else if (lag < 50) {
            setTICK_MS(50);
        } else if (lag < 80) {
            setTICK_MS(80);
        } else if (lag < 100) {
            setTICK_MS(100);
        } else if (lag >= 100) {
            setTICK_MS(125);
        }

    }

}
