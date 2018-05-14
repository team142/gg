/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.controller.OrbManager;
import com.team142.gg.server.controller.PowerManager;
import com.team142.gg.server.controller.runnable.TickerComms;
import com.team142.gg.server.controller.runnable.TickerPhysics;
import com.team142.gg.server.controller.runnable.powers.Power;
import com.team142.gg.server.controller.runnable.powers.Power01Shoot;
import com.team142.gg.server.model.mappable.artificial.Bullet;
import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageScoreboard;
import lombok.Data;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author just1689
 */
@Data
public final class Player {

    private final String id;
    private final long joinTimeMs;
    private final AtomicInteger kills;
    private final AtomicInteger deaths;
    private final Tank TANK;
    private final int TAG;
    private final List<Bullet> BULLETS;
    private final String KEY_FORWARD = "W";
    private final String KEY_BACKWARD = "S";
    private final String KEY_LEFT = "A";
    private final String KEY_RIGHT = "D";
    private final ConcurrentHashMap.KeySetView<String, Boolean> SPEECH_HEARD = new ConcurrentHashMap<String, String>().newKeySet();
    private String name;
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
        this.TANK = new Tank(new SpaceTimePoint(0, 0, 0.8), "default", Server.TANK_DEFAULT_SPEED, TAG, 100, this);
        this.name = "";
        this.powers = new ConcurrentHashMap<>();
        Power01Shoot power1Shoot = new Power01Shoot(this);
        addPower(power1Shoot);
        addPower(" ", power1Shoot);

    }

    public boolean haveIHeard(String text) {
        if (SPEECH_HEARD.contains(text)) {
            return true;
        }
        SPEECH_HEARD.add(text);
        return false;
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

        GameMap map = Repository.GAMES_ON_SERVER.get(gameId).getMap();

        if (isRegularMovement()) {
            if (isKeyDown(KEY_FORWARD)) {
                getTANK().moveForward(map);
            }
            if (isKeyDown(KEY_LEFT)) {
                turnLeft();
            } else if (isKeyDown(KEY_RIGHT)) {
                turnRight();
            }
        } else if (isReverseMovement()) {
            getTANK().moveBackward(map);
            if (isKeyDown(KEY_LEFT)) {
                turnRight();
            } else if (isKeyDown(KEY_RIGHT)) {
                turnLeft();
            }
        }

        getTANK().movementTick(map);

    }

    private boolean isRegularMovement() {
        return !isKeyDown(KEY_BACKWARD)
                && (isKeyDown(KEY_FORWARD) || isKeyDown(KEY_LEFT) || isKeyDown(KEY_RIGHT));
    }

    private boolean isReverseMovement() {
        return !isKeyDown(KEY_FORWARD)
                && isKeyDown(KEY_BACKWARD);
    }

    private void turnRight() {
        getTANK().rotateRight();
    }

    private void turnLeft() {
        getTANK().rotateLeft();
    }

    public void checkForOrbs() {
        Orb orb = OrbManager.isTankInOrb(TANK, gameId);
        if (orb != null) {
            PowerManager.givePlayerRandomPower(this);
            OrbManager.remove(orb);
            new Thread(() -> {
                Repository.GAMES_ON_SERVER.get(getGameId()).getSoundManager().sendYipee();
            }).start();

        }
    }

    public void addPower(Power power) {
        addPower(String.valueOf(power.getKey()), power);
    }

    public void addPower(String key, Power power) {
        powers.put(key, power);
    }

}
