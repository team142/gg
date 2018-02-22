/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageRadar;
import static java.lang.Math.toIntExact;

/**
 *
 * @author just1689
 */
public class Power06Radar extends Power {

    private static final long INITIAL_COOLDOWN = 5000;

    public Power06Radar(Player player) {
        super(6, player, 0, INITIAL_COOLDOWN, 1, "6");
    }

    @Override
    public void execute() {
        MessageRadar message = new MessageRadar(Repository.GAMES_ON_SERVER.get(getPlayer().getGameId()), toIntExact(getRefreshTime()));
        MessageManager.sendPlayerAMessage(getPlayer().getId(), message);

    }

    @Override
    public void nofityLevelChange() {
        setRefreshTime(INITIAL_COOLDOWN * getLevel());
    }

}
