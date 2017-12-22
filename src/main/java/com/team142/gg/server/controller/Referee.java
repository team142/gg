/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Server;

/**
 *
 * @author just1689
 */
public class Referee {

    public static void playerJoinsGame(String playerId, String gameId) {
        Game game = Server.GAMES_ON_SERVER.get(gameId);
        if (game == null) {
            //TODO: tell the user that the game does not exist
            return;
        }

        Player player = Server.PLAYERS_ON_SERVER.get(playerId);
        if (player == null) {
            //TODO: log no player found...
            return;
        }

        game.playerJoins(player);

    }

}
