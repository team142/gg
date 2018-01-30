/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.controller.runnable.base.AbstractTickerWorker;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;

/**
 *
 * @author just1689
 */
public class TickerPhysics extends AbstractTickerWorker {

    public TickerPhysics(String playerId, String gameId) {
        super(playerId, gameId);
    }

    @Override
    public void doTick() {
        Player player = Repository.PLAYERS_ON_SERVER.get(getPLAYER_ID());
        player.getTANK().movementTick(Repository.GAMES_ON_SERVER.get(getGAME_ID()).getMap());

        player.getBULLETS()
                .stream()
                .filter((bullet) -> !bullet.movementTickBullet())
                .forEach((bullet) -> bullet.getPlayer().removeBullet(bullet));

    }

}
