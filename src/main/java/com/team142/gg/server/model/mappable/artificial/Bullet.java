/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.artificial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.mappable.organic.SkinType;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.mappable.meta.MovableElement;
import com.team142.gg.server.utils.PhysicsUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class Bullet extends MovableElement {

    @Getter
    @Setter
    @JsonIgnore
    private Player player;

    @Getter
    private boolean ok;

    @Setter
    private double damage;

    public Bullet(Player player) {
        super(player.getTANK().getX(), player.getTANK().getY(), player.getTANK().getZ(), SkinType.BULLET.name(), Server.DEFAULT_SPEED, -1);
        this.setDirection(1);
        this.setRotation(player.getTANK().getRotation());
        this.player = player;
        this.damage = 50; //WHO KNOWs..
        this.ok = true;
        if (getX() < 0) {
            setX(0);
        }
        if (getZ() < 0) {
            setZ(0);
        }

    }

    public void movementTickBullet() {
        if (!ok) {
            return;
        }
        movementTick();
        Repository.GAMES_ON_SERVER
                .get(player.getGameId())
                .getTANKS()
                .values()
                .stream()
                .filter((tank) -> tank.getTAG() != player.getTAG())
                .filter((tank) -> PhysicsUtils.isTinyObjectInLarger(tank, this, tank.getWidth()))
                .forEach((tank) -> damage(tank));

        if (getX() < 0) {
            ok = false;
            return;
        }
        if (getZ() < 0) {
            ok = false;
            return;
        }
        if (getX() > 50 + 1) { //NEED TO USE MAP SIZE?
            ok = false;
            return;
        }
        if (getZ() > 50 + 1) {
            ok = false;
        }

    }

    public void damage(Tank tank) {
        BulletHitResult result = tank.damage(damage, player);

        Game game = Repository.GAMES_ON_SERVER.get(player.getGameId());
        game.getSoundManager().sendDing();
        Player toPlayer = Repository.PLAYERS_ON_SERVER.get(tank.getPlayerId());

        Logger.getLogger(this.getClass().getName())
                .log(
                        Level.INFO,
                        "{0} hit {1}",
                        new String[]{player.getName(), toPlayer.getName()}
                );

        if (result.isLethal()) {
            GameManager.handleKill(game, player, toPlayer);
        }
        ok = false;
    }

}
