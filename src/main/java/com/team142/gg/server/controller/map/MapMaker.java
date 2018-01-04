/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.map;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.mappable.DirectionTypes;
import com.team142.gg.server.model.mappable.MapTileElement;

/**
 *
 * @author just1689
 */
public class MapMaker {

    public static void generateMap(MapSettings settings, Game game) {
        //TODO

        //Green map for now
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                MapTileElement tile = new MapTileElement(x, 1, y, TerrainType.GRASS, DirectionTypes.DIR0, TileType.FLAT_TILE);
                game.getMAP().add(tile);
            }
        }

    }

}
