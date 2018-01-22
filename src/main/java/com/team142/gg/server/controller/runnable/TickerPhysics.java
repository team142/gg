/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.controller.runnable.base.AbstractTickerWorker;

/**
 *
 * @author just1689
 */
public class TickerPhysics extends AbstractTickerWorker {

    public TickerPhysics(Player player) {
        super(player);
    }

    @Override
    public void doTick() {
        getPLAYER().getTANK().movementTick();
        getPLAYER().getBULLETS().forEach((bullet) -> bullet.movementTickBullet());
        getPLAYER().getBULLETS()
                .stream()
                .filter((bullet) -> !bullet.isOk())
                .forEach((bullet) -> bullet.getPlayer().removeBullet(bullet));

    }

}
