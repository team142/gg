/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.controller.PowerManager;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;

/**
 *
 * @author just1689
 */
public class Power08Teleport extends Power {

    public Power08Teleport(Player player, long refreshTime) {
        super(player, 0, refreshTime);
    }

    @Override
    public void execute() {
        PowerManager.sendCooldown(getPlayer().getId(), this, 8);
        GameManager.spawn(Repository.GAMES_ON_SERVER.get(getPlayer().getGameId()), getPlayer());

    }

}
