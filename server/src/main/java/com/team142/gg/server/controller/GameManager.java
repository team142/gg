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
import com.team142.gg.server.model.messages.incoming.MessageJoinGame;
import com.team142.gg.server.model.messages.base.ViewType;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageBullet;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageScoreboard;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageShareMap;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageSpray;
import com.team142.gg.server.utils.Reporter;
import com.team142.gg.server.view.ViewManager;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author just1689
 */
public class GameManager {

    private static final Logger LOG = Logger.getLogger(GameManager.class.getName());

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
        playerJoins(game, player);

    }

    public static void playerJoins(Game game, Player player) {
        //Change state
        player.setGameId(game.getId());
        game.getTANKS().put(player.getId(), player.getTANK());
        player.getTANK().setMaxHealth(game.getStartHealth());
        game.getPlayers().add(player);
        spawn(game, player);

        //Start threads
        player.start();

        //Commnuicate
        welcomePlayerToGame(player.getId());
        announcePlayerJoins(game, player);
        sendMapToPlayer(player.getId(), game);
        game.getSoundManager().sendSpawn();
        Reporter.report();

    }

    public static void spawn(Game game, Player player) {
        boolean success = false;
        int x = 0;
        int z = 0;

        while (!success) {
            x = ThreadLocalRandom.current().nextInt(1, 48 + 1);
            z = ThreadLocalRandom.current().nextInt(1, 48 + 1);
            success = game.getMap().isMovable(x, z);
        }

        player.getTANK().setHealth(player.getTANK().getMaxHealth());
        player.getTANK().setX(x);
        player.getTANK().setZ(z);

    }

    private static void welcomePlayerToGame(String playerId) {
        LOG.log(Level.INFO, "Welcoming player ({0}) to game", new String[]{playerId});
        ViewManager.changePlayerView(playerId, ViewType.VIEW_CANVAS);

    }

    public static void announcePlayerJoins(Game game, Player player) {
        sendScoreBoard(game);

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

    public static void handleKill(Game game, Player fromPlayer, Player player) {
        fromPlayer.getKills().incrementAndGet();
        GameManager.spawn(game, player);
        GameManager.sendScoreBoard(game);
        MessageManager.sendPlayersAMessage(game, new MessageSpray(player, 1200));
        game.getSoundManager().sendExplode();
    }

}
