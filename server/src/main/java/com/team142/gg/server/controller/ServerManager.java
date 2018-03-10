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
import com.team142.gg.server.model.messages.incoming.MessageJoinServer;
import com.team142.gg.server.model.messages.outgoing.other.MessageListOfGames;
import com.team142.gg.server.model.messages.base.ViewType;
import com.team142.gg.server.model.messages.outgoing.other.MessageShareTag;
import com.team142.gg.server.utils.Reporter;
import com.team142.gg.server.view.ViewManager;
import org.springframework.web.socket.WebSocketSession;

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

        LOG.log(Level.INFO, "Checking for env");
        SERVER_NAME = System.getenv("REPORT_SERVER_STATS_AS");
        REPORT_STATS = SERVER_NAME != null && !SERVER_NAME.isEmpty();

    }

    public static void checkSession(WebSocketSession session) {
        if (!session.isOpen()) {
            Logger.getLogger(Server.class.getName()).log(Level.INFO, "Player disconnected");
            ServerManager.playerDisconnects(session.getId());
        }

    }

    public static void handle(MessageJoinServer body) {
        String name = ensureUniqueName(body.getName());
        Repository.PLAYERS_ON_SERVER.get(body.getFrom()).setName(name);
        ViewManager.changePlayerView(body.getFrom(), ViewType.VIEW_GAMES);
        ServerManager.notifyPlayerOfGames(body.getFrom());
        LOG.log(Level.INFO, "New person on server is: {0}", name);

    }

    public static void notifyPlayerOfGames(String playerId) {
        LOG.log(Level.INFO, "Telling player about games... ");
        MessageManager.sendPlayerAMessage(playerId, new MessageListOfGames(Repository.GAMES_ON_SERVER.values()));

    }

    public static void notifyNewConnection(WebSocketSession session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Added player: {0}", id);
        Repository.SESSIONS_ON_SERVER.put(id, session);
        newPlayer(id);

    }

    public static void newPlayer(String id) {
        Player player = new Player(id);
        Repository.PLAYERS_ON_SERVER.put(player.getId(), player);
        MessageManager.sendPlayerAMessage(player.getId(), new MessageShareTag(player.getTAG()));

    }

    public static void notifyDisconnection(WebSocketSession session) {
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
        LOG.log(Level.INFO, "Creating the default game");
        createGame("Default game", new MapSettings(50, 50));

    }

    public static Game createGame(String name, MapSettings settings) {
        LOG.log(Level.INFO, "Creating a new game");
        Game game = new Game(name);
        MapManager.generateMap(settings, game);
        Repository.GAMES_ON_SERVER.put(game.getId(), game);
        return game;

    }

    public static void playerDisconnects(String id) {

        Player player = Repository.PLAYERS_ON_SERVER.get(id);
        Game game = Repository.GAMES_ON_SERVER.get(player.getGameId());

        if (game != null) {
            game.removePlayer(player);
        }
        player.stop();

        Repository.PLAYERS_ON_SERVER.remove(id);
        Repository.SESSIONS_ON_SERVER.remove(id);

        Reporter.reportLeaves();

    }

}
