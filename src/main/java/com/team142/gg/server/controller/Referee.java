/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.MessageChangeView;
import com.team142.gg.server.model.messages.base.ViewType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Referee {

    public static void playerJoinsGame(String playerId, String gameId) {

        Game game = Server.GAMES_ON_SERVER.get(gameId);

        if (game == null) {
            Logger.getLogger(Referee.class.getName()).log(Level.SEVERE, "Player ({0}) tried to join null game ({1}) ", new String[]{playerId, gameId});
            return;
        }

        Player player = Server.PLAYERS_ON_SERVER.get(playerId);
        if (player == null) {
            Logger.getLogger(Referee.class.getName()).log(Level.SEVERE, "Null Player ({0}) tried to game ({1}) ", new String[]{playerId, gameId});
            return;
        }

        game.playerJoins(player);

        welcomePlayerToGame(playerId);
    }

    private static void welcomePlayerToGame(String playerId) {
        
        //Tell player
        MessageChangeView message = new MessageChangeView(ViewType.VIEW_CANVAS);
        PostOffice.sendObjectToPlayer(playerId, message);

    }

}
