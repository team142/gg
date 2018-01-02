/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

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
    private String model;

    public MapTileElement(BigDecimal x, BigDecimal y, BigDecimal z, String skin, int rotation, String model) {
        super(x, y, z, skin, rotation);
        this.model = model;

    }

}
