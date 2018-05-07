/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.controller.runnable.powers.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author just1689
 */
public class Repository {

    public static final ConcurrentHashMap<String, Player> PLAYERS_ON_SERVER = new ConcurrentHashMap<String, Player>();
    public static final ConcurrentHashMap<String, Game> GAMES_ON_SERVER = new ConcurrentHashMap<String, Game>();
    public static final ConcurrentHashMap<String, WebSocketSession> SESSIONS_ON_SERVER = new ConcurrentHashMap<String, WebSocketSession>();

    public static boolean hasPlayerByName(String name) {
        return Repository.PLAYERS_ON_SERVER
                .values()
                .stream()
                .anyMatch((p) -> (p.getName().equals(name)));

    }

    public static final ArrayList<Class> POWER_CLASSES = new ArrayList<>();

    static {
        POWER_CLASSES.add(Power10HpMech.class);
        POWER_CLASSES.add(Power01Shoot.class);
        POWER_CLASSES.add(Power02RearShoot.class);
        POWER_CLASSES.add(Power03Missile.class);
        POWER_CLASSES.add(Power04WeakSeekerMissle.class);
        POWER_CLASSES.add(Power05DropBomb.class);
        POWER_CLASSES.add(Power06Radar.class);
        POWER_CLASSES.add(Power07Intel.class);
        POWER_CLASSES.add(Power08Teleport.class);
        POWER_CLASSES.add(Power09Hop180.class);

    }

}
