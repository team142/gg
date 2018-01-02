/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import java.math.BigDecimal;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MovableElement extends PlaceableElement {

    private BigDecimal speed;

    @Getter
    private final int changeX, changeY, changeZ;

    public MovableElement(BigDecimal x, BigDecimal y, BigDecimal z, String skin, BigDecimal speed, int changeX, int changeY, int changeZ) {
        super(x, y, z, skin, 0);
        this.speed = speed;
        this.changeX = changeX;
        this.changeY = changeY;
        this.changeZ = changeZ;

    }

    public void movementTick() {
        //TODO

    }
}
