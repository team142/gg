/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.meta;

import com.team142.gg.server.model.Map;
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
    private double speed;

    @Getter
    @Setter
    private double diagonalSpeed;

    @Getter
    @Setter
    private double direction;

    public MovableElement(double x, double y, double z, String skin, double speed, int tag) {
        super(x, y, z, BigDecimal.ZERO, skin, tag);
        this.speed = speed;
        BigDecimal diagSpeed = new BigDecimal(speed);
        BigDecimal speedD = new BigDecimal(speed);
        diagSpeed = diagSpeed.multiply(speedD).divide(new BigDecimal(2));
        diagSpeed = MathUtils.sqrt(diagSpeed);
        diagSpeed = diagSpeed.setScale(3, RoundingMode.HALF_UP);
        this.diagonalSpeed = diagSpeed.doubleValue();

    }

    public void movementTick(Map map) {
        if (direction == 0) {
            return;
        }
//        System.out.println("Direction: " + direction);

        if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR0) == 0) {
            changeZ(speed, 1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR0) == 0) {
            changeZ(speed, -1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR1) == 0) {
            changeX(diagonalSpeed, 1, map);
            changeZ(diagonalSpeed, 1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR1) == 0) {
            changeX(diagonalSpeed, -1, map);
            changeZ(diagonalSpeed, -1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR2) == 0) {
            changeX(speed, 1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR2) == 0) {
            changeX(speed, -1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR3) == 0) {
            changeX(diagonalSpeed, 1, map);
            changeZ(diagonalSpeed, -1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR3) == 0) {
            changeX(diagonalSpeed, -1, map);
            changeZ(diagonalSpeed, 1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR4) == 0) {
            changeZ(speed, -1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR4) == 0) {
            changeZ(speed, 1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR5) == 0) {
            changeX(diagonalSpeed, -1, map);
            changeZ(diagonalSpeed, -1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR5) == 0) {
            changeX(diagonalSpeed, 1, map);
            changeZ(diagonalSpeed, 1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR6) == 0) {
            changeX(speed, -1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR6) == 0) {
            changeX(speed, 1, map);

        } else if (direction == 1 && getRotation().compareTo(DirectionTypes.DIR7) == 0) {
            changeX(diagonalSpeed, -1, map);
            changeZ(diagonalSpeed, 1, map);
        } else if (direction == -1 && getRotation().compareTo(DirectionTypes.DIR7) == 0) {
            changeX(diagonalSpeed, 1, map);
            changeZ(diagonalSpeed, -1, map);
        }

        if (getX() < 0) {
            setX(0);
        }
        if (getX() > 49 + 1) {
            setX(49);
        }
        if (getZ() < 0) {
            setZ(0);
        }
        if (getZ() > 49 + 1) {
            setZ(49);
        }

    }

    private void changeZ(double amt, int dir, Map map) {
        double newZ = getZ() + (amt * dir);
        if (dir == 1 && map.isMovable(getX(), newZ + 1)) {
            setZ(newZ);
        } else if (dir == -1 && map.isMovable(getX(), newZ)) {
            setZ(newZ);
        }

    }

    private void changeX(double amt, int dir, Map map) {
        double newX = getX() + (amt * dir);
        if (dir == 1 && map.isMovable(newX + 1, getZ())) {
            setX(newX);
        } else if (dir == -1 && map.isMovable(newX, getZ())) {
            setX(newX);
        }

    }
}
