/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.organic;

import java.util.HashMap;

/**
 *
 * @author just1689
 */
public class SkinTypeLibrary {

    public static final HashMap<SkinType, Integer> TERRAIN_SKIN_COUNT = new HashMap<>();

    static {
        TERRAIN_SKIN_COUNT.put(SkinType.WATER, 3);
        TERRAIN_SKIN_COUNT.put(SkinType.GRASS, 3);
        TERRAIN_SKIN_COUNT.put(SkinType.ROCK, 3);
    }

}
