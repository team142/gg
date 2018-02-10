/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.organic;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MapTileElement extends PlaceableElement {

    @Getter
    private final String model;

    public MapTileElement(SpaceTimePoint point, Tile tile, float rotation) {
        super(point, rotation, tile.getSkin(), 0);
        this.model = tile.getModelType().name();
    }

}
