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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Server {

    public static final int TICK_MS = 17;
    public static final double DEFAULT_SPEED = 0.125;
    public static final AtomicInteger TAGS = new AtomicInteger(1000);

    public static String SERVER_NAME;
    public static boolean REPORT_STATS;
    public static final String REPORT_URL = "https://us-central1-good-game-192610.cloudfunctions.net/function-newPlayer-v1";

    static {
        createDefaultGame();

        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Checking for env");
        String env = System.getenv("REPORT_SERVER_STATS_AS");
        if (env != null && !env.isEmpty()) {
            SERVER_NAME = env;
            REPORT_STATS = true;
            Logger.getLogger(Server.class.getName()).log(Level.INFO, "Stats reporting is on: {0}", SERVER_NAME);
        } else {
            Logger.getLogger(Server.class.getName()).log(Level.INFO, "No reporting to stats server");
        }

    }

    private static void createDefaultGame() {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating the default game");
        createGame("Default game", new MapSettings("meh"));

    }

    public static Game createGame(String name, MapSettings settings) {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating a new game");
        Game game = new Game(name);
        MapMaker.generateMap(settings, game);
        Repository.GAMES_ON_SERVER.put(game.getId(), game);
        return game;

    }

    public static void playerDisconnects(String id) {
        Player player = Repository.PLAYERS_ON_SERVER.get(id);
        player.getGame().removePlayer(player);
        player.setGame(null);
        player.stop();
        Repository.PLAYERS_ON_SERVER.remove(id);
        Repository.SESSIONS_ON_SERVER.remove(id);

    }

    public static Game getGameByPlayer(String id) {
        return Repository.GAMES_ON_SERVER.values().stream()
                .filter(e -> e.hasPlayer(id))
                .findFirst()
                .orElse(null);
    }

    public static void newPlayer(Player player) {
        Repository.PLAYERS_ON_SERVER.put(player.getId(), player);
        PostOffice.sendPlayerAMessage(player.getId(), new MessageShareTag(player.getTAG()));

    }

    public static boolean hasPlayerByName(String name) {
        return Repository.PLAYERS_ON_SERVER
                .values()
                .stream()
                .anyMatch((p) -> (p.getName().equals(name)));

    }

}
