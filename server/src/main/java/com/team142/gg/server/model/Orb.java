/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.utils.PhysicsUtils;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class Orb {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private double x, z;

    @Getter
    private String gameId;

    public Orb(String gameId) {
        this.gameId = gameId;
    }

    public void use() {
        //Ummm
    }

    public void check() {
//        Repository.GAMES_ON_SERVER
//        .get(getGameId())
//        .getTANKS()
//        .values()
//        .stream()
//        .filter((tank) -> tank.getTAG() != player.getTAG())
//        .filter((tank) -> PhysicsUtils.isTinyObjectInLarger(tank, this, tank.getWidth()))
//        .forEach(this::pickup);        
    }

    public void pickup(Tank tank) {
        //
    }

}
