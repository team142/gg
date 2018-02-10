/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.model.mappable.organic.MapSettings;
import com.team142.gg.server.model.mappable.organic.ModelType;
import com.team142.gg.server.model.mappable.organic.SkinType;
import com.team142.gg.server.model.mappable.organic.Tile;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Map;
import com.team142.gg.server.model.mappable.organic.MapTileElement;

/**
 *
 * @author just1689
 */
public class MapManager {

    private static final Tile WATER_TILE = new Tile(SkinType.WATER, ModelType.FLAT_TILE, false, false);
    private static final Tile GRASS_TILE = new Tile(SkinType.GRASS, ModelType.FLAT_TILE, true, false);
    private static final Tile ROCK_TILE = new Tile(SkinType.ROCK, ModelType.ROCK_TILE, false, true);

    public static void generateMap(MapSettings settings, Game game) {
        //TODO

        game.setMap(new Map(settings.getXTiles(), settings.getZTiles()));

        //Green map for now, surrounded by water & rock in the middleF
        for (int x = 0; x < settings.getXTiles(); x++) {
            for (int y = 0; y < settings.getZTiles(); y++) {
                if (x > 20 && x < 30 && y > 20 && y < 30) {
                    createRock(x, y, game);
                } else if (x == 49 || y == 49 || x == 0 || y == 0) {
                    createWater(x, y, game);
                } else {
                    createGrass(x, y, game);
                }
            }
        }

        //Draw a line of water down the middle of the map
        int x = 25;
        for (int y = 0; y < settings.getZTiles(); y++) {
            if (!(y >= 10 && y <= 40)) {
                createWater(x, y, game);
            }
        }

        //More water
        int y = 25;
        for (x = 0; x < settings.getZTiles(); x++) {
            if (x >= 10 && x <= 40) {
                createWater(x, y, game);
            }
        }

    }

    public static void createGrass(int x, int y, Game game) {
        createTile(x, y, game, GRASS_TILE, true, true);

    }

    public static void createRock(int x, int y, Game game) {
        createTile(x, y, game, ROCK_TILE, false, false);

    }

    public static void createWater(int x, int y, Game game) {
        createTile(x, y, game, WATER_TILE, false, true);

    }

    public static void createTile(int x, int y, int z, Game game, Tile tile, boolean movable, boolean shootover) {
        MapTileElement mapTileElement = new MapTileElement(new SpaceTimePoint(x,y,z, 0.0f), tile);
        game.getMap().getTILES().add(mapTileElement);
        game.getMap().setTileBitmapMovable(x, z, movable);
        game.getMap().setTileBitmapShootover(x, z, shootover);

    }

    public static void createTile(int x, int z, Game game, Tile tile, boolean movable, boolean shootover) {
        createTile(x, 1, z, game, tile, movable, shootover);

    }

}
