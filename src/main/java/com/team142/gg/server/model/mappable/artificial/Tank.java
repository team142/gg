/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.artificial;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.controller.ServerManager;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.mappable.meta.MovableElement;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageSpray;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class Tank extends MovableElement {

    @Getter
    @Setter
    private double health;

    @Getter
    @Setter
    private double maxHealth;

    @Getter
    @Setter
    private String playerId;

    public Tank(double x, double y, double z, String skin, double speed, int tag, double hp, Player player) {
        super(x, y, z, skin, speed, tag);
        this.health = hp;
        this.maxHealth = hp;
        this.playerId = player.getId();
    }

    public void damage(double dmg, Player fromPlayer) {
        health -= dmg;

        if (health <= 0) {
            Repository.PLAYERS_ON_SERVER.get(playerId).addDeath();
            Game game = ServerManager.getGameByPlayer(playerId);
            game.spawn(Repository.PLAYERS_ON_SERVER.get(playerId));
            fromPlayer.addKill();
            GameManager.sendScoreBoard(ServerManager.getGameByPlayer(playerId));
            MessageManager.sendPlayersAMessage(game, new MessageSpray(Repository.PLAYERS_ON_SERVER.get(playerId).getTAG(), 1200));
            game.getSoundManager().sendDing();
            game.getSoundManager().sendExplode();

        } else {
            ServerManager.getGameByPlayer(playerId).getSoundManager().sendDing();
        }

        System.out.println(
                "Damage! "
                + dmg
                + " from "
                + fromPlayer.getName()
                + " to "
                + Repository.PLAYERS_ON_SERVER.get(playerId).getName()
                + " hp now: " + health
        );

    }

}
