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
public class MovableElement extends PlaceableElement {

    @Getter
    @Setter
    private BigDecimal speed;

    public MovableElement(BigDecimal x, BigDecimal y, BigDecimal z, String skin, BigDecimal speed) {
        super(x, y, z, skin, 0);
        this.speed = speed;

    }

    public void movementTick() {
        System.out.println("Speed: " + speed.toPlainString() + ", " + getRotation());
        if (speed.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        switch (getRotation()) {
            case 2:
                getY().add(speed);
                break;
            case 4:
                getY().subtract(speed);
                break;
            case 3:
                getX().add(speed);
                break;
            case 1:
                getX().subtract(speed);
                break;
            default:
                break;
        }

    }
}
