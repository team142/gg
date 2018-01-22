/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;

/**
 *
 * @author just1689
 */
public class Repository {

    public static final ConcurrentHashMap<String, Player> PLAYERS_ON_SERVER = new ConcurrentHashMap<String, Player>();
    public static final ConcurrentHashMap<String, Game> GAMES_ON_SERVER = new ConcurrentHashMap<String, Game>();
    public static final ConcurrentHashMap<String, Session> SESSIONS_ON_SERVER = new ConcurrentHashMap<String, Session>();

    public static Game getGameByPlayer(String id) {
        return GAMES_ON_SERVER
                .values()
                .stream()
                .filter(e -> e.hasPlayer(id))
                .findFirst()
                .orElse(null);
    }

    public static boolean hasPlayerByName(String name) {
        return Repository.PLAYERS_ON_SERVER
                .values()
                .stream()
                .anyMatch((p) -> (p.getName().equals(name)));

    }

}
