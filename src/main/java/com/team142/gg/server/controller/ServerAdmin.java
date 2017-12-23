/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.MessageChangeView;
import com.team142.gg.server.model.messages.MessageListOfGames;
import com.team142.gg.server.model.messages.ViewType;
import javax.websocket.Session;

/**
 *
 * @author just1689
 */
public class ServerAdmin {

    public static void changePlayerView(String playerId, ViewType view) {
        MessageChangeView message = new MessageChangeView(view);
        PostOffice.sendObjectToPlayer(playerId, message);

    }

    public static void notifyPlayerOfGames(String playerId) {
        MessageListOfGames message = new MessageListOfGames();
        Server.GAMES_ON_SERVER.values().forEach((game) -> {
            message.getGAMES().add(game.toGameSummary());
        });
        System.out.println("Telling player about games: ");
        PostOffice.sendObjectToPlayer(playerId, message);

    }

    public static void notifyNewConnection(Session session) {
        String id = session.getId();
        System.out.println("Added player: " + id);
        Server.SESSIONS_ON_SERVER.put(id, session);
        Player player = new Player(id);
        Server.newPlayer(player);
    }

    public static void notifyDisconnection(Session session) {
        String id = session.getId();
        System.out.println("Removed player: " + id);
        Server.SESSIONS_ON_SERVER.remove(id);
        Server.playerDisconnects(id);

    }

    public static void playerHasAName(String id, String name) {
        Server.PLAYERS_ON_SERVER.get(id).setName(name);
        changePlayerView(id, ViewType.VIEW_GAMES);
        ServerAdmin.notifyPlayerOfGames(id);

    }
}
