/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.runnable.powers;

import com.team142.gg.server.model.Player;

/**
 *
 * @author just1689
 */
public class Power03Missile extends Power {

    public Power03Missile(Player player, long refreshTime) {
        super(3, player, 0, refreshTime);
    }

    @Override
    public void execute() {

    }

}
