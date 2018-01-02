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

    @Getter
    private final BigDecimal speed;

    public MovableElement(BigDecimal x, BigDecimal y, BigDecimal z, String skin, BigDecimal speed) {
        super(x, y, z, skin, 0);
        this.speed = speed;

    }

    public void movementTick() {
        if (speed.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        switch (getRotation()) {
            case 0:
                getY().add(speed);
                break;
            case 180:
                getY().subtract(speed);
                break;
            case 90:
                getX().add(speed);
                break;
            case 270:
                getX().subtract(speed);
                break;
            default:
                break;
        }

    }
}
