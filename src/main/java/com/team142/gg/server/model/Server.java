/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.controller.PostOffice;
import com.team142.gg.server.controller.map.MapMaker;
import com.team142.gg.server.controller.map.MapSettings;
import com.team142.gg.server.model.messages.outgoing.other.MessageShareTag;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
    public static final int TICK_MS = 80;
    public static final BigDecimal DEFAULT_SPEED = new BigDecimal(0.125);
    public static final AtomicInteger TAGS = new AtomicInteger(1000);
    
    static {
        createDefaultGame();
        
    }
    
    private static void createDefaultGame() {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating the default game");
        createGame("Default game", new MapSettings("meh"));
        
    }
    
    public static Game createGame(String name, MapSettings settings) {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating a new game");
        Game game = new Game(name);
        MapMaker.generateMap(settings, game);
        GAMES_ON_SERVER.put(game.getId(), game);
        game.start();
        return game;
        
    }
    
    public static void playerDisconnects(String id) {
        Server.PLAYERS_ON_SERVER.remove(id);
        GAMES_ON_SERVER.values().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .ifPresent((game) -> game.removePlayer(id));
        SESSIONS_ON_SERVER.remove(id);
        
    }
    
    public static Game getGameByPlayer(String id) {
        return GAMES_ON_SERVER.values().stream()
                .filter(e -> e.hasPlayer(id))
                .findFirst()
                .orElse(null);
    }
    
    public static void newPlayer(Player player) {
        PLAYERS_ON_SERVER.put(player.getId(), player);
        PostOffice.sendPlayerAMessage(player.getId(), new MessageShareTag(player.getTAG()));
        
    }
    
    public Game getGameByPlayer(Player player) {
        if (player == null) {
            return null;
        }
        return getGameByPlayer(player.getId());
    }
    
}
