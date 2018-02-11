/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.artificial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.GameMap;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.model.mappable.organic.SkinType;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.mappable.meta.MovableElement;
import com.team142.gg.server.utils.PhysicsUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        super(new SpaceTimePoint(player.getTANK().getPoint()), SkinType.BULLET.name(), Server.BULLET_DEFAULT_SPEED, -1);
        this.getPoint().setRotation(player.getTANK().getPoint().getRotation());
        this.player = player;
        this.damage = 35; //WHO KNOWs..
        this.ok = true;
        if (getPoint().getX() < 0) {
            getPoint().setX(0);
        }
        if (getPoint().getZ() < 0) {
            getPoint().setZ(0);
        }
        this.setWalkOnWater(true);

    }

    public boolean movementTickBullet() {
        if (!ok) {
            return ok; //False
        }

        GameMap map = (Repository.GAMES_ON_SERVER.get(player.getGameId()).getMap());
        boolean success = moveForward(map);
        if (!success) {
            ok = false;
            return ok;
        }

        Repository.GAMES_ON_SERVER
                .get(player.getGameId())
                .getTANKS()
                .values()
                .stream()
                .filter((tank) -> tank.getTAG() != player.getTAG())
                .filter((tank) -> PhysicsUtils.isTinyObjectInLarger(tank, this, tank.getWidth()))
                .forEach(this::damage);

        if (!ok) {
            return false;
        }

        if (getPoint().getX() < 0) {
            ok = false;
            return ok;
        }
        if (getPoint().getZ() < 0) {
            ok = false;
            return ok;
        }
        if (getPoint().getX() > map.getX() + 1) {
            ok = false;
            return ok;
        }
        if (getPoint().getZ() > map.getZ() + 1) {
            ok = false;
            return ok;
        }

        if (!Repository.GAMES_ON_SERVER.get(player.getGameId()).getMap().isShootover(getPoint().getX(), getPoint().getZ())) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Bullet is in something.. delete");
            ok = false;
        }
        return ok;

    }

    public void damage(Tank tank) {
        if (!ok) {
            return;
        }
        ok = false;
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
    }

}
