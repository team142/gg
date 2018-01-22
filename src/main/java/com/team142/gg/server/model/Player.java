/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.mappable.artificial.Bullet;
import com.team142.gg.server.model.mappable.meta.DirectionTypes;
import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageScoreboard;
import com.team142.gg.server.workers.TickerComms;
import com.team142.gg.server.workers.TickerPhysics;
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
    private Game game;
    private final TickerComms tickerComms;
    private final TickerPhysics tickerPhysics;

    public Player(String id) {
        this.BULLETS = new CopyOnWriteArrayList<>();
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        this.TAG = Server.TAGS.incrementAndGet();
        this.TANK = new Tank(0, 0.25d, 0, "default", Server.DEFAULT_SPEED, TAG, 100, this);
        this.tickerPhysics = new TickerPhysics(this);
        this.tickerComms = new TickerComms(this);
        this.name = "";

    }

    public void addKill() {
        this.kills.addAndGet(1);
    }

    public void addDeath() {
        this.deaths.addAndGet(1);
    }

    public void populateScorebord(MessageScoreboard board) {
        //Add score
        int score = this.kills.get() - this.deaths.get();
        board.getSCORES().put(name, score < 0 ? 0 : score);

        //Add tag
        board.getTAGS().put(name, TAG);

    }

    public void keyDown(String key) {
        switch (key) {
            case "A":
                TANK.setRotation(TANK.getRotation().subtract(DirectionTypes.ONE_TICK_ROTATE));
                if (TANK.getRotation().compareTo(DirectionTypes.DIR0) < 0) {
                    TANK.setRotation(DirectionTypes.DIR7);
                }
                return;
            case "W":
                TANK.setDirection(1);
                return;
            case "D":
                TANK.setRotation(TANK.getRotation().add(DirectionTypes.ONE_TICK_ROTATE));
                if (TANK.getRotation().compareTo(DirectionTypes.DIR8) >= 0) {
                    TANK.setRotation(DirectionTypes.DIR0);
                }
                return;
            case "S":
                TANK.setDirection(-1);
                return;
            case " ":
                attemptShoot();
                return;
            default:
                break;
        }

        TANK.setDirection(0);

    }

    public void keyUp(String key) {
        TANK.setDirection(0);
    }

    private void attemptShoot() {
        //Check last shot
        if (System.currentTimeMillis() - LAST_BULLET.get() >= MS_PER_SHOT) {
            //We can shoot
            LAST_BULLET.set(System.currentTimeMillis());
            shoot();

        }

    }

    private void shoot() {
        //Create a bullet
        Bullet bullet = new Bullet(this);

        //Add to player (Game will send to players)
        BULLETS.add(bullet);

        //Tell referee (send to game)
        GameManager.sendBullet(game, bullet);

        game.getSoundManager().sendShoot();

    }

    public void start() {
        System.out.println("Starting threads for: " + getName());
        new Thread(tickerPhysics).start();
        new Thread(tickerComms).start();

    }

    public void stop() {
        tickerPhysics.getRUNNING().set(false);
        tickerComms.getRUNNING().set(false);

    }

    public void removeBullet(Bullet bullet) {
        System.out.println("Removed bullet");
        BULLETS.remove(bullet);
        bullet.setPlayer(null);
    }

}
