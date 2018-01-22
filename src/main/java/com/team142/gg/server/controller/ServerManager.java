/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.outgoing.other.MessageChangeView;
import com.team142.gg.server.model.messages.incoming.MessageJoinServer;
import com.team142.gg.server.model.messages.outgoing.other.MessageListOfGames;
import com.team142.gg.server.model.messages.base.ViewType;
import javax.websocket.Session;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class ServerManager {

    private static final Logger LOG = Logger.getLogger(ServerManager.class.getName());

    public static void changePlayerView(String playerId, ViewType view) {
        MessageManager.sendPlayerAMessage(playerId, new MessageChangeView(view));
    }

    public static void handle(MessageJoinServer body) {
        String name = ensureUniqueName(body.getName());
        Repository.PLAYERS_ON_SERVER.get(body.getFrom()).setName(name);
        changePlayerView(body.getFrom(), ViewType.VIEW_GAMES);
        ServerManager.notifyPlayerOfGames(body.getFrom());

    }

    public static void notifyPlayerOfGames(String playerId) {
        MessageListOfGames message = new MessageListOfGames();
        Repository.GAMES_ON_SERVER.values().forEach((game) -> {
            message.getGAMES().add(game.toGameSummary());
        });
        LOG.log(Level.INFO, "Telling player about games: ");
        MessageManager.sendPlayerAMessage(playerId, message);

    }

    public static void notifyNewConnection(Session session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Added player: {0}", id);
        Repository.SESSIONS_ON_SERVER.put(id, session);
        Player player = new Player(id);
        Server.newPlayer(player);

    }

    public static void notifyDisconnection(Session session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Removed player: {0}", id);
        Server.playerDisconnects(id);

    }

    public static String ensureUniqueName(String in) {
        boolean present = true;
        int num = 0;
        String test = in;
        while (present) {
            test = num == 0 ? in : in + num;
            present = Server.hasPlayerByName(test);
            num++;
        }
        return test;

    }

}
