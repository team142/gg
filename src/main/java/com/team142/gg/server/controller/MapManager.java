/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.mappable.organic.MapSettings;
import com.team142.gg.server.model.mappable.organic.TileBitmap;
import com.team142.gg.server.model.mappable.organic.ModelType;
import com.team142.gg.server.model.mappable.organic.SkinType;
import com.team142.gg.server.model.mappable.organic.Tile;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.mappable.meta.DirectionTypes;
import com.team142.gg.server.model.mappable.organic.MapTileElement;

/**
 *
 * @author just1689
 */
public class MapManager {

    public static void generateMap(MapSettings settings, Game game) {
        //TODO

        Tile waterTile = new Tile(SkinType.WATER, ModelType.FLAT_TILE, false, false);
        Tile grassTile = new Tile(SkinType.GRASS, ModelType.FLAT_TILE, true, false);
        Tile rockTile = new Tile(SkinType.ROCK, ModelType.ROCK_TILE, false, true);

        int width = 50;
        int length = 50;

        game.setBitmap(new TileBitmap[width][length]);

        //Green map for now
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {
                if (x > 20 && x < 30 && y > 20 && y < 30) {
                    MapTileElement tile = new MapTileElement(x, 1, y, rockTile, DirectionTypes.DIR0);
                    game.getMAP().add(tile);
                    game.getBitmap()[x][y] = new TileBitmap(false, false);
                } else if (x == 49 || y == 49) {
                    MapTileElement tile = new MapTileElement(x, 1, y, waterTile, DirectionTypes.DIR0);
                    game.getMAP().add(tile);
                    game.getBitmap()[x][y] = new TileBitmap(false, false);
                } else {
                    MapTileElement tile = new MapTileElement(x, 1, y, grassTile, DirectionTypes.DIR0);
                    game.getMAP().add(tile);
                    game.getBitmap()[x][y] = new TileBitmap(true, true);
                }
            }
        }

    }

}
