/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.model.mappable.MovableElement;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

    private Set KEYS = ConcurrentHashMap.newKeySet();

    public Player(String id) {
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        TANK = new MovableElement(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, "default", BigDecimal.ZERO);

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
        KEYS.add(key);
        findDirection();
    }

    public void keyUp(String key) {
        KEYS.remove(key);
        findDirection();
    }

    public boolean isDown(String key) {
        return KEYS.contains(key);
    }

    private void findDirection() {
        if (KEYS.contains("A")) {
            TANK.setRotation(2);
            return;
        }
        if (KEYS.contains("W")) {
            TANK.setSpeed(BigDecimal.ONE);
            TANK.setRotation(1);
            return;
        }
        if (KEYS.contains("D")) {
            TANK.setRotation(3);
            return;
        }
        if (KEYS.contains("S")) {
            TANK.setRotation(4);
            TANK.setSpeed(BigDecimal.valueOf(-1L));
        }
    }

}
