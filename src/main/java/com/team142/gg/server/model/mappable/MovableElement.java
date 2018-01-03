/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import com.team142.gg.server.utils.MathUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Getter
    @Setter
    private BigDecimal diagonalSpeed;

    @Getter
    @Setter
    private double direction;

    public MovableElement(BigDecimal x, BigDecimal y, BigDecimal z, String skin, BigDecimal speed) {
        super(x, y, z, BigDecimal.ZERO, skin);
        this.speed = speed;
        this.diagonalSpeed = speed;
        this.diagonalSpeed = this.diagonalSpeed.multiply(speed).divide(new BigDecimal(2));
        this.diagonalSpeed = MathUtils.sqrt(this.diagonalSpeed);
        this.diagonalSpeed.setScale(3, RoundingMode.HALF_UP);

    }

    public void movementTick() {
        if (direction == 0) {
            return;
        }
//        System.out.println("Direction: " + direction);

        if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR0) == 0) {
            setZ(getZ().add(speed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR0) == 0) {
            setZ(getZ().subtract(speed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR1) == 0) {
            setX(getX().add(diagonalSpeed));
            setZ(getZ().add(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR1) == 0) {
            setX(getX().subtract(diagonalSpeed));
            setZ(getZ().subtract(diagonalSpeed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR2) == 0) {
            setX(getX().add(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR2) == 0) {
            setX(getX().subtract(diagonalSpeed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR3) == 0) {
            setX(getX().add(diagonalSpeed));
            setZ(getZ().subtract(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR3) == 0) {
            setX(getX().subtract(diagonalSpeed));
            setZ(getZ().add(diagonalSpeed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR4) == 0) {
            setZ(getZ().subtract(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR4) == 0) {
            setZ(getZ().add(diagonalSpeed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR5) == 0) {
            setX(getX().subtract(diagonalSpeed));
            setZ(getZ().subtract(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR5) == 0) {
            setX(getX().add(diagonalSpeed));
            setZ(getZ().add(diagonalSpeed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR6) == 0) {
            setX(getX().subtract(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR6) == 0) {
            setX(getX().add(diagonalSpeed));

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR7) == 0) {
            setX(getX().subtract(diagonalSpeed));
            setZ(getZ().add(diagonalSpeed));
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR7) == 0) {
            setX(getX().add(diagonalSpeed));
            setZ(getZ().subtract(diagonalSpeed));
        }

//        if (direction == 1 || direction == -1) {
//            if (getRotation().compareTo(DirectionTypes.DIR0) == 0 || getRotation().compareTo(DirectionTypes.DIR4) == 0) {
//                System.out.println("Is direction 1");
//                if (direction == 1) {
//                    setZ(getZ().add(speed));
//                } else if (direction == -1) {
//                    setZ(getZ().subtract(speed));
//                }
//
//            } else if (getRotation().compareTo(DirectionTypes.DIR1) == 0 || getRotation().compareTo(DirectionTypes.DIR5) == 0) {
//                System.out.println("Is direction 1");
//                setX(getX().add(diagonalSpeed.multiply(new BigDecimal(direction))));
//                setZ(getZ().add(diagonalSpeed.multiply(new BigDecimal(direction))));
//            } else {
//                System.out.println("Was actually rotation: " + getRotation() + ", " + DirectionTypes.DIR2);
//
//            }
//
//        }
//        if (direction == 13) {
//            setZ(getZ().add(speed));
//        } else if (direction == 5) {
//            setZ(getZ().subtract(speed));
//        } else if (direction == 3) {
//            setX(getX().add(speed));
//        } else if (direction == 7) {
//            setX(getX().subtract(speed));
//        }
    }
}
