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

    public Player(String id) {
        this.id = id;
        this.joinTimeMs = System.currentTimeMillis();
        this.kills = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        TANK = new MovableElement(BigDecimal.ZERO, new BigDecimal(0.5), BigDecimal.ZERO, "default", new BigDecimal(0.5));
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
//        KEYS.remove(key);
//        findDirection();
        TANK.setDirection(0);
    }

//    private void findDirection() {
//
//        System.out.println("-----START-----");
//        KEYS.forEach(k -> System.out.println(k));
//
//        //TODO: more refined implementation
//        //For now
//        /*
//        812
//        703
//        654
//         */
//        if (KEYS.contains("A")) {
////            TANK.setDirection(7);
//            TANK.setRotation(270);
//            return;
//        }
//        if (KEYS.contains("W")) {
//            TANK.setDirection(1);
//            return;
//        }
//        if (KEYS.contains("D")) {
////            TANK.setDirection(3);
//            TANK.setRotation(90);
//            return;
//        }
//        if (KEYS.contains("S")) {
//            TANK.setDirection(5);
//            return;
//        }
//
//        TANK.setDirection(0);
//
//    }
}
