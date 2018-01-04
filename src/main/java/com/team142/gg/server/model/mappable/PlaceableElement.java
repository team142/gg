/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
public class PlaceableElement {

    @Getter
    @Setter
    private double x, y, z;

    @Getter
    private BigDecimal rotation;

    @Getter
    @Setter
    private String skin;

    @Getter
    private final int TAG;

    public void setRotation(BigDecimal rotation) {
        this.rotation = rotation.setScale(3, RoundingMode.HALF_UP);
    }

}
