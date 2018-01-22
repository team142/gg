/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageShareThingsDynamic;
import com.team142.gg.server.controller.runnable.base.AbstractTickerWorker;

/**
 *
 * @author just1689
 */
public class TickerComms extends AbstractTickerWorker {

    private long lastPing = 0;

    public TickerComms(Player player) {
        super(player);
    }

    @Override
    public void doTick() {
        checkPing();
        MessageManager.sendPlayerAMessage(getPLAYER().getId(), getDynamicThingsMessage());

    }

    private MessageShareThingsDynamic getDynamicThingsMessage() {
        return new MessageShareThingsDynamic(getPLAYER().getGame());
    }

    private void checkPing() {
        if (lastPing == 0) {
            ping();
        }

    }

    public void ping() {
        lastPing = System.currentTimeMillis();
        Repository.SESSIONS_ON_SERVER.get(getPLAYER().getId()).getAsyncRemote().sendText("0");

    }

    public void pong() {
        long lag = System.currentTimeMillis() - lastPing;
//        System.out.println("PING roundtrip: " + lag);

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
//        System.out.println("Now: " + getTICK_MS());

    }

}
