/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
    private int score = 0;

    private Set KEYS = ConcurrentHashMap.newKeySet();

    public Player(String id) {
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
    }

    public void addKill() {
        score++;
    }

    public void addDeath() {
        if (score > 0) {
            score--;
        }
    }

    public void addScoreToBoard(Map<String, Integer> map) {
        map.put(name, score);
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
