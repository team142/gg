/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.controller.PowerManager;
import com.team142.gg.server.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
@Data
public abstract class Power implements Runnable {

    private final int ID;
    private Player player;
    private long lastRunTime;
    private long refreshTime; //In MS
    private int level;
    private String key;

    @Override
    public void run() {

        //Do not run if not refreshed
        if (lastRunTime + refreshTime > System.currentTimeMillis()) {
            return;
        }

        //Do the powers specfics
        execute();

        //Save last time
        lastRunTime = System.currentTimeMillis();

        PowerManager.sendCooldown(getPlayer().getId(), this, key);

    }

    //Implementation for that power
    public abstract void execute();

    public boolean incrementLevel() {
        if (level >= 10) {
            return false;
        }
        level++;
        return true;
    }

    //Implementation for that power
    public abstract void nofityLevelChange();

    
    
}
