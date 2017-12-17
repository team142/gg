/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author just1689
 */
public class Server {

    public static final ConcurrentHashMap<String, Player> PLAYERS_ON_SERVER = new ConcurrentHashMap<String, Player>();
    public static final ConcurrentHashMap<String, Game> GAMES_ON_SERVER = new ConcurrentHashMap<String, Game>();

    public Game getGameByPlayer(String id) {
        for (Map.Entry<String, Game> entry : GAMES_ON_SERVER.entrySet()) {
            if (entry.getValue().hasPlayer(id)) {
                return entry.getValue();
            }
        }
        //TODO: log no game found by player
        return null;
    }

    public Game getGameByPlayer(Player player) {
        if (player == null) {
            return null;
        }
        return getGameByPlayer(player.getId());
    }

}
