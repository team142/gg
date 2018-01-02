/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
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
public class ServerAdmin {

    private static final Logger LOG = Logger.getLogger(ServerAdmin.class.getName());

    public static void changePlayerView(String playerId, ViewType view) {
        MessageChangeView message = new MessageChangeView(view);
        PostOffice.sendPlayerAMessage(playerId, message);

    }

    public static void handle(MessageJoinServer body) {
        Server.PLAYERS_ON_SERVER.get(body.getFrom()).setName(body.getName());
        changePlayerView(body.getFrom(), ViewType.VIEW_GAMES);
        ServerAdmin.notifyPlayerOfGames(body.getFrom());

    }

    public static void notifyPlayerOfGames(String playerId) {
        MessageListOfGames message = new MessageListOfGames();
        Server.GAMES_ON_SERVER.values().forEach((game) -> {
            message.getGAMES().add(game.toGameSummary());
        });
        LOG.log(Level.INFO, "Telling player about games: ");
        PostOffice.sendPlayerAMessage(playerId, message);

    }

    public static void notifyNewConnection(Session session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Added player: {0}", id);
        Server.SESSIONS_ON_SERVER.put(id, session);
        Player player = new Player(id);
        Server.newPlayer(player);

    }

    public static void notifyDisconnection(Session session) {
        String id = session.getId();
        LOG.log(Level.INFO, "Removed player: {0}", id);
        Server.SESSIONS_ON_SERVER.remove(id);
        Server.playerDisconnects(id);

    }

}
