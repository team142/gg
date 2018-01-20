/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import com.team142.gg.server.controller.PostOffice;
import com.team142.gg.server.controller.Referee;
import com.team142.gg.server.controller.Sounds;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Server;
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
            Server.PLAYERS_ON_SERVER.get(playerId).addDeath();
            Game game = Server.getGameByPlayer(playerId);
            game.spawn(Server.PLAYERS_ON_SERVER.get(playerId));
            fromPlayer.addKill();
            Referee.sendScoreBoard(Server.getGameByPlayer(playerId));
            PostOffice.sendPlayersAMessage(game, new MessageSpray(Server.PLAYERS_ON_SERVER.get(playerId).getTAG(), 1200));
            Sounds.sendExplode(game);

        } else {
            Sounds.sendDing(Server.getGameByPlayer(playerId));
        }

        System.out.println(
                "Damage! "
                + dmg
                + " from "
                + fromPlayer.getName()
                + " to "
                + Server.PLAYERS_ON_SERVER.get(playerId).getName()
                + " hp now: " + health
        );

    }

}
