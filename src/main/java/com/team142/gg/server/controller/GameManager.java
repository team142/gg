/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.mappable.artificial.Bullet;
import com.team142.gg.server.model.messages.outgoing.other.MessageChangeView;
import com.team142.gg.server.model.messages.incoming.MessageJoinGame;
import com.team142.gg.server.model.messages.base.ViewType;
import com.team142.gg.server.model.messages.incoming.MessageKeyDown;
import com.team142.gg.server.model.messages.incoming.MessageKeyUp;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageBullet;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageScoreboard;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageShareMap;
import com.team142.gg.server.utils.Reporter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author just1689
 */
public class GameManager {

    private static final Logger LOG = Logger.getLogger(GameManager.class.getName());

    public static void handle(MessageKeyDown messageKeyDown) {
        Repository.PLAYERS_ON_SERVER.get(messageKeyDown.getFrom()).keyDown(messageKeyDown.getKey());
    }

    public static void handle(MessageKeyUp messageKeyUp) {
        Repository.PLAYERS_ON_SERVER.get(messageKeyUp.getFrom()).keyUp(messageKeyUp.getKey());
    }

    public static void handle(MessageJoinGame body) {
        Game game = Repository.GAMES_ON_SERVER.get(body.getId());
        if (game == null) {
            LOG.log(Level.SEVERE, "Player ({0}) tried to join null game ({1}) ", new String[]{body.getFrom(), body.getId()});
            return;
        }

        Player player = Repository.PLAYERS_ON_SERVER.get(body.getFrom());
        if (player == null) {
            LOG.log(Level.SEVERE, "Null Player ({0}) tried to game ({1}) ", new String[]{body.getFrom(), body.getId()});
            return;
        }
        player.setGameId(game.getId());
        game.playerJoins(player);
        welcomePlayerToGame(body.getFrom());
        announcePlayerJoins(game, player);
        GameManager.sendMapToPlayer(player.getId(), game);
        Reporter.REPORT_THREAD_POOL.execute(() -> MessageManager.reportNewPlayerForStats(player.getId()));
        game.getSoundManager().sendSpawn();

    }

    private static void welcomePlayerToGame(String playerId) {
        LOG.log(Level.INFO, "Welcoming player ({0}) to game", new String[]{playerId});
        MessageManager.sendPlayerAMessage(playerId, new MessageChangeView(ViewType.VIEW_CANVAS));

    }

    public static void announcePlayerJoins(Game game, Player player) {
        sendScoreBoard(game);
        //TODO: announce

    }

    public static void sendScoreBoard(Game game) {
        MessageManager.sendPlayersAMessage(game, new MessageScoreboard(game));

    }

    public static void sendMapToPlayer(String playerId, Game game) {
        MessageManager.sendPlayerAMessage(playerId, new MessageShareMap(game));
    }

    public static void sendMapToPlayers(Game game) {
        MessageManager.sendPlayersAMessage(game, new MessageShareMap(game));
    }

    public static void sendBullet(Game game, Bullet bullet) {
        MessageManager.sendPlayersAMessage(game, new MessageBullet(bullet));
    }

}
