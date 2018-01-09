/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import com.team142.gg.server.controller.map.terrain.Tile;
import java.math.BigDecimal;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MapTileElement extends PlaceableElement {

    @Getter
    private String model;

    public MapTileElement(double x, double y, double z, Tile tile, BigDecimal rotation) {
        super(x, y, z, rotation, tile.getSkin(), 0);
        this.model = tile.getModelType().name();

    }

}
