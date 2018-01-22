/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller.map.terrain;

import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
@Data
public class Tile {

    private SkinType skinType;
    private ModelType modelType;
    private boolean traversable;
    private boolean high;

    public String getSkin() {
        return "/textures/"
                + skinType.name().toLowerCase()
                + ThreadLocalRandom.current().nextInt(1, SkinTypeLibrary.TERRAIN_SKIN_COUNT.get(skinType) + 1)
                + "-min"
                + ".jpg";

    }

}
