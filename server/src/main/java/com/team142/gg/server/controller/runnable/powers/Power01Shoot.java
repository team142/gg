/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.mappable.artificial.Bullet;

/**
 * @author just1689
 */
public class Power01Shoot extends Power {

    private static final int INITIAL_COOLDOWN = 1000;

    public Power01Shoot(Player player) {
        super(1, player, 0, INITIAL_COOLDOWN, 1, "1");
    }

    @Override
    public void execute() {
        playerShoots();

    }

    public void playerShoots() {
        Game game = Repository.GAMES_ON_SERVER.get(getPlayer().getGameId());

        //Change state
        Bullet bullet = getPlayer().createBullet();

        bullet.setDamage(bullet.getDamage() + getLevelLessOne() * 10);
        bullet.setSpeed(bullet.getSpeed() + getLevelLessOne() * Server.BULLET_INCREMENT_SPEED);

        //Communicate
        GameManager.sendBullet(game, bullet);
        game.getSoundManager().sendShoot();

    }

    @Override
    public void nofityLevelChange() {
        setRefreshTime(INITIAL_COOLDOWN / getLevel());
    }

}
