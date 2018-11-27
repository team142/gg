/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.utils.PhysicsUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author just1689
 */
public class Orb extends PlaceableElement {

    @Getter
    private final String gameId;
    @Getter
    @Setter
    SpaceTimePoint point;
    @Getter
    @Setter
    private String name;

    public Orb(String name, String gameId, SpaceTimePoint point, String skin, int TAG) {
        super(TAG, point, skin);
        this.name = name;
        this.point = point;
        this.gameId = gameId;
    }

    public void use() {
        //Ummm
    }

    public void check() {
        Repository.GAMES_ON_SERVER
                .get(getGameId())
                .getTANKS()
                .values()
                .stream()
                .filter((tank) -> PhysicsUtils.isTinyObjectInLarger(tank.getPoint(), getPoint(), tank.getWidth()))
                .forEach(this::pickup);
    }

    public void pickup(Tank tank) {
        //
    }

}
