/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.map;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author just1689
 */
public class TerrainSkins {

    public static final HashMap<TerrainType, Integer> TERRAIN_SKIN_COUNT = new HashMap<>();

    static {
        TERRAIN_SKIN_COUNT.put(TerrainType.WATER, 3);
        TERRAIN_SKIN_COUNT.put(TerrainType.GRASS, 3);
        TERRAIN_SKIN_COUNT.put(TerrainType.ROCK, 3);
    }

    public static String getRandomSkin(TerrainType type) {
        return type.name() + ThreadLocalRandom.current().nextInt(1, TERRAIN_SKIN_COUNT.get(type) + 1);

    }

}
