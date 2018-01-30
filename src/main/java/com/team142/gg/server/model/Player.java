/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.model.mappable.artificial.Bullet;
import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageScoreboard;
import com.team142.gg.server.controller.runnable.TickerComms;
import com.team142.gg.server.controller.runnable.TickerPhysics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class Player {

    private final String id;
    private String name;
    private final long joinTimeMs;
    private final AtomicInteger kills;
    private final AtomicInteger deaths;
    private final Tank TANK;
    private final int TAG;
    private final AtomicLong LAST_BULLET = new AtomicLong(0);
    private int MS_PER_SHOT = 1000;
    private final List<Bullet> BULLETS;
    private String gameId;
    private TickerComms tickerComms;
    private TickerPhysics tickerPhysics;

    public Player(String id) {
        this.BULLETS = new CopyOnWriteArrayList<>();
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        this.TAG = Server.TAGS.incrementAndGet();
        this.TANK = new Tank(0, 0.16d, 0, "default", Server.DEFAULT_SPEED, TAG, 100, this);
        this.name = "";

    }

    public void populateScorebord(MessageScoreboard board) {
        //Add score
        board.getSCORES().put(name, this.kills.get());

        //Add tag
        board.getTAGS().put(name, TAG);

    }

    public Bullet createBullet() {
        Bullet bullet = new Bullet(this);
        BULLETS.add(bullet);
        return bullet;

    }

    public void start() {
        this.tickerPhysics = new TickerPhysics(id, gameId);
        this.tickerComms = new TickerComms(id, gameId);
        new Thread(tickerPhysics).start();
        new Thread(tickerComms).start();

    }

    public void stop() {
        tickerPhysics.getRUNNING().set(false);
        tickerComms.getRUNNING().set(false);

    }

    public void removeBullet(Bullet bullet) {
        BULLETS.remove(bullet);
        bullet.setPlayer(null);
    }

}
