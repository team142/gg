/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

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

    private Set KEYS = ConcurrentHashMap.newKeySet();

    public Player(String id) {
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
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
    }

    public void keyUp(String key) {
        KEYS.remove(key);
    }

    public boolean isDown(String key) {
        return KEYS.contains(key);
    }

}
