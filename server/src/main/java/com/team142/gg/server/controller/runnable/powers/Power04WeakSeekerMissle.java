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
import com.team142.gg.server.utils.PhysicsUtils;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author just1689
 */
public class Power04WeakSeekerMissle extends Power {

    private static final long INITIAL_COOLDOWN = 5000;

    public Power04WeakSeekerMissle(Player player) {
        super(4, player, 0, INITIAL_COOLDOWN, 1);
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
        bullet.setDamage(100);

        int which = ThreadLocalRandom.current().nextInt(0, game.getPlayers().size());

        boolean found = false;
        int triesLeft = 10;
        while (!found && triesLeft > 0) {
            Player player = game.getPlayers().get(which);
            if (getPlayer().getId().equals(player.getId())) {
                triesLeft--;
                continue;
            }
            float radians = PhysicsUtils.getRadians(getPlayer().getTANK().getPoint(), player.getTANK().getPoint());
            bullet.getPoint().setRotation(radians);
            found = true;
        }

        GameManager.sendBullet(game, bullet);
        game.getSoundManager().sendShoot();

    }

}
