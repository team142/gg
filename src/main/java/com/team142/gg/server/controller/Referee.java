/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.outgoing.other.MessageChangeView;
import com.team142.gg.server.model.messages.incoming.MessageJoinGame;
import com.team142.gg.server.model.messages.base.ViewType;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageScoreboard;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Referee {

    public static void handle(MessageJoinGame body) {

        Game game = Server.GAMES_ON_SERVER.get(body.getId());

        if (game == null) {
            Logger.getLogger(Referee.class.getName()).log(Level.SEVERE, "Player ({0}) tried to join null game ({1}) ", new String[]{body.getFrom(), body.getId()});
            return;
        }

        Player player = Server.PLAYERS_ON_SERVER.get(body.getFrom());
        if (player == null) {
            Logger.getLogger(Referee.class.getName()).log(Level.SEVERE, "Null Player ({0}) tried to game ({1}) ", new String[]{body.getFrom(), body.getId()});
            return;
        }
        game.playerJoins(player);
        welcomePlayerToGame(body.getFrom());
    }

    private static void welcomePlayerToGame(String playerId) {
        Logger.getLogger(Referee.class.getName()).log(Level.INFO, "Welcoming player ({0}) to game", new String[]{playerId});
        //Tell player
        MessageChangeView message = new MessageChangeView(ViewType.VIEW_CANVAS);
        PostOffice.sendPlayerAMessage(playerId, message);

    }

    public static void announcePlayerJoins(Game game, Player player) {
        MessageScoreboard message = new MessageScoreboard(game);
        PostOffice.sendPlayersAMessage(game, message);
    }

}
