/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.Player;

/**
 *
 * @author just1689
 */
public class Power10HpMech extends Power {
    
    private static final long INITIAL_COOLDOWN = 10000;
    
    public Power10HpMech(Player player) {
        super(10, player, 0, INITIAL_COOLDOWN, 1, "0");
    }
    
    @Override
    public void execute() {
        getPlayer().getTANK().heal(50 + (getLevel() - 1) * 10);
        GameManager.notifyHealthChange(getPlayer().getGameId(), getPlayer().getTANK());
    }
    
    @Override
    public void nofityLevelChange() {
        setRefreshTime(INITIAL_COOLDOWN * (1 - getLevel() / 15));
        
    }
    
}
