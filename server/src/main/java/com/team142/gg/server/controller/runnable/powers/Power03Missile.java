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
import com.team142.gg.server.model.mappable.artificial.Bullet;

/**
 *
 * @author just1689
 */
public class Power03Missile extends Power {

    private static final long INITIAL_COOLDOWN = 15000;

    public Power03Missile(Player player) {
        super(3, player, 0, INITIAL_COOLDOWN, 1, "3");
    }

    @Override
    public void execute() {
        playerShoots();

    }

    public void playerShoots() {
        Game game = Repository.GAMES_ON_SERVER.get(getPlayer().getGameId());

        //Change state
        Bullet bullet = getPlayer().createBullet();
        bullet.setSpeed(bullet.getSpeed() * 2);
        bullet.setDamage(100 + getLevel() * 10);

        //Communicate
        GameManager.sendBullet(game, bullet);
        game.getSoundManager().sendShoot();


    }

    @Override
    public void nofityLevelChange() {
        setRefreshTime(INITIAL_COOLDOWN * (1 - getLevel() / 11));
    }
}
