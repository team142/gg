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
import com.team142.gg.server.controller.runnable.powers.Power;
import com.team142.gg.server.controller.runnable.powers.Power1Shoot;
import com.team142.gg.server.controller.runnable.powers.Power7Intel;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final List<Bullet> BULLETS;
    private String gameId;
    private TickerComms tickerComms;
    private TickerPhysics tickerPhysics;
    private ConcurrentHashMap<String, Power> powers;

    private ConcurrentSkipListSet<String> keyboard = new ConcurrentSkipListSet<>();

    public Player(String id) {
        this.BULLETS = new CopyOnWriteArrayList<>();
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        this.TAG = Server.TAGS.incrementAndGet();
        this.TANK = new Tank(0, 0.16d, 0, "default", Server.DEFAULT_SPEED, TAG, 100, this);
        this.name = "";
        this.powers = new ConcurrentHashMap<>();
        Power1Shoot power1Shoot = new Power1Shoot(this, 1000);
        this.powers.put("1", power1Shoot);
        this.powers.put(" ", power1Shoot);
        this.powers.put("7", new Power7Intel(this, 1000));

    }

    public void populateScorebord(MessageScoreboard board) {
        //Add score
        board.getScores().put(name, this.kills.get());

        //Add tag
        board.getTags().put(name, TAG);

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

    public void keyUp(String key) {
        keyboard.remove(key);
    }

    public void keyDown(String key) {
        keyboard.add(key);
    }

    public boolean isKeyDown(String key) {
        return keyboard.contains(key);
    }

    public void movementTick() {
        if (isKeyDown("A")) {
            getTANK().rotateLeft();
        } else if (isKeyDown("D")) {
            getTANK().rotateRight();
        }

        Map map = Repository.GAMES_ON_SERVER.get(gameId).getMap();
        if (isKeyDown("W")) {
            getTANK().moveForward(map);
        } else if (isKeyDown("S")) {
            getTANK().moveBackward(map);
        }

        getTANK().movementTick(map);

    }

}
