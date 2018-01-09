/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.map;

import com.team142.gg.server.controller.map.terrain.ModelType;
import com.team142.gg.server.controller.map.terrain.SkinType;
import com.team142.gg.server.controller.map.terrain.Tile;
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

        Tile waterTile = new Tile(SkinType.WATER, ModelType.FLAT_TILE, false, false);
        Tile grassTile = new Tile(SkinType.GRASS, ModelType.FLAT_TILE, true, false);
        Tile rockTile = new Tile(SkinType.ROCK, ModelType.FLAT_TILE, false, true);

        //Green map for now
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                if (x > 20 && x < 30) {
                    MapTileElement tile = new MapTileElement(x, 1, y, rockTile, DirectionTypes.DIR0);
                    game.getMAP().add(tile);
                } else {
                    MapTileElement tile = new MapTileElement(x, 1, y, grassTile, DirectionTypes.DIR0);
                    game.getMAP().add(tile);
                }
            }
        }

    }

}
