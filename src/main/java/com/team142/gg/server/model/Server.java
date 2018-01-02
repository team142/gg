/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author just1689
 */
public class Server {

    public static final ConcurrentHashMap<String, Player> PLAYERS_ON_SERVER = new ConcurrentHashMap<String, Player>();
    public static final ConcurrentHashMap<String, Game> GAMES_ON_SERVER = new ConcurrentHashMap<String, Game>();
    public static final ConcurrentHashMap<String, Session> SESSIONS_ON_SERVER = new ConcurrentHashMap<String, Session>();

    public static final int TICK_MS = 50;

    static {
        createDefaultGame();

    }

    private static void createDefaultGame() {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating the default game");
        Game game = new Game("Default Game");
        GAMES_ON_SERVER.put(game.getId(), game);

    }

    public static void playerDisconnects(String id) {
        Server.PLAYERS_ON_SERVER.remove(id);
        GAMES_ON_SERVER.entrySet().stream()
                .filter(e -> e.getValue().getId().equals(id))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent((game) -> game.removePlayer(id));

    }

    public static Game getGameByPlayer(String id) {
        return GAMES_ON_SERVER.entrySet().stream()
                .filter(e -> e.getValue().hasPlayer(id))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public static void newPlayer(Player player) {
        PLAYERS_ON_SERVER.put(player.getId(), player);
    }

    public Game getGameByPlayer(Player player) {
        if (player == null) {
            return null;
        }
        return getGameByPlayer(player.getId());
    }

}
