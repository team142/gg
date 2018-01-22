/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.Server;
import static com.team142.gg.server.model.Server.REPORT_STATS;
import static com.team142.gg.server.model.Server.SERVER_NAME;
import com.team142.gg.server.model.mappable.organic.MapSettings;
import com.team142.gg.server.model.messages.outgoing.other.MessageChangeView;
import com.team142.gg.server.model.messages.incoming.MessageJoinServer;
import com.team142.gg.server.model.messages.outgoing.other.MessageListOfGames;
import com.team142.gg.server.model.messages.base.ViewType;
import com.team142.gg.server.model.messages.outgoing.other.MessageShareTag;
import javax.websocket.Session;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class ServerManager {

    private static final Logger LOG = Logger.getLogger(ServerManager.class.getName());

    static {
        ServerManager.createDefaultGame();

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

    public static void changePlayerView(String playerId, ViewType view) {
        MessageManager.sendPlayerAMessage(playerId, new MessageChangeView(view));
    }

    public static void handle(MessageJoinServer body) {
        String name = ensureUniqueName(body.getName());
        Repository.PLAYERS_ON_SERVER.get(body.getFrom()).setName(name);
        LOG.log(Level.INFO, "New person is: {0}", body.getFrom());
        changePlayerView(body.getFrom(), ViewType.VIEW_GAMES);
        ServerManager.notifyPlayerOfGames(body.getFrom());

    }

    public static void notifyPlayerOfGames(String playerId) {
        MessageListOfGames message = new MessageListOfGames();
        Repository.GAMES_ON_SERVER.values().forEach((game) -> {
            message.getGAMES().add(game.toGameSummary());
        });
        LOG.log(Level.INFO, "Telling player about games... ");
        MessageManager.sendPlayerAMessage(playerId, message);

    }

    public static void notifyNewConnection(Session session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Added player: {0}", id);
        Repository.SESSIONS_ON_SERVER.put(id, session);
        Player player = new Player(id);
        newPlayer(player);

    }

    public static void notifyDisconnection(Session session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Removed player: {0}", id);
        playerDisconnects(id);

    }

    public static String ensureUniqueName(String in) {
        boolean present = true;
        int num = 0;
        String test = in;
        while (present) {
            test = num == 0 ? in : in + num;
            present = Repository.hasPlayerByName(test);
            num++;
        }
        return test;

    }

    public static void createDefaultGame() {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating the default game");
        createGame("Default game", new MapSettings("meh"));

    }

    public static Game createGame(String name, MapSettings settings) {
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Creating a new game");
        Game game = new Game(name);
        MapManager.generateMap(settings, game);
        Repository.GAMES_ON_SERVER.put(game.getId(), game);
        return game;

    }

    public static void playerDisconnects(String id) {

        Game game = Repository.getGameByPlayer(id);
        Player player = Repository.PLAYERS_ON_SERVER.get(id);

        game.removePlayer(player);
        player.stop();

        Repository.PLAYERS_ON_SERVER.remove(id);
        Repository.SESSIONS_ON_SERVER.remove(id);

    }

    public static void newPlayer(Player player) {
        Repository.PLAYERS_ON_SERVER.put(player.getId(), player);
        MessageManager.sendPlayerAMessage(player.getId(), new MessageShareTag(player.getTAG()));

    }

}
