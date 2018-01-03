/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.model.mappable.DirectionTypes;
import com.team142.gg.server.model.mappable.MovableElement;
import java.math.BigDecimal;
import java.util.Map;
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
    private final MovableElement TANK;
    private final int TAG;

    public Player(String id) {
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        TAG = Server.TAGS.incrementAndGet();
        TANK = new MovableElement(BigDecimal.ZERO, new BigDecimal(0.25), BigDecimal.ZERO, "default", Server.DEFAULT_SPEED, TAG);
    }

    public void addKill() {
        this.kills.addAndGet(1);
    }

    public void addDeath() {
        this.deaths.addAndGet(1);
    }

    public void addScoreToBoard(Map<String, Integer> map) {
        int score = this.kills.get() - this.deaths.get();
        map.put(name, score < 0 ? 0 : score);
    }

    public void keyDown(String key) {
        if (key.equals("A")) {
            TANK.setRotation(TANK.getRotation().subtract(DirectionTypes.ONE_TICK_ROTATE));
            if (TANK.getRotation().compareTo(DirectionTypes.DIR0) < 0) {
                TANK.setRotation(DirectionTypes.DIR7);
            }
//            System.out.println("Direction: " + TANK.getRotation().toPlainString());
            return;
        }
        if (key.equals("W")) {
            TANK.setDirection(1);
            return;
        }
        if (key.equals("D")) {
            TANK.setRotation(TANK.getRotation().add(DirectionTypes.ONE_TICK_ROTATE));
            if (TANK.getRotation().compareTo(DirectionTypes.DIR8) >= 0) {
                TANK.setRotation(DirectionTypes.DIR0);
            }
//            System.out.println("Direction: " + TANK.getRotation().toPlainString());
            return;
        }
        if (key.equals("S")) {
            TANK.setDirection(-1);
            return;
        }

        TANK.setDirection(0);

    }

    public void keyUp(String key) {
        TANK.setDirection(0);
    }

}
