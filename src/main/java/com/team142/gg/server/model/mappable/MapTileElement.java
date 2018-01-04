/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import com.team142.gg.server.controller.map.TerrainSkins;
import com.team142.gg.server.controller.map.TerrainType;
import com.team142.gg.server.controller.map.TileType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class MapTileElement extends PlaceableElement {

    @Getter
    @Setter
    private TileType model;

    public MapTileElement(double x, double y, double z, TerrainType type, BigDecimal rotation, TileType model) {
        super(x, y, z, rotation, TerrainSkins.getRandomSkin(type), 0);
        this.model = model;
        

    }

}
