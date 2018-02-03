/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.meta;

import com.team142.gg.server.model.Map;
import com.team142.gg.server.model.Player;
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

    public static final float BASE_ROTATE = (float) Math.toRadians(1.25);
    public static final float MAX_ROTATE = (float) (Math.PI * 2);
    public static final float HALF_PI = (float) (Math.PI / 2);

    public MovableElement(double x, double y, double z, String skin, double speed, int tag) {
        super(x, y, z, 0, skin, tag);
        this.speed = speed;
        BigDecimal speedD = new BigDecimal(speed);
        BigDecimal diagSpeed = new BigDecimal(speed);
        diagSpeed = diagSpeed.multiply(speedD).divide(new BigDecimal(2));
        diagSpeed = MathUtils.sqrt(diagSpeed);
        diagSpeed = diagSpeed.setScale(3, RoundingMode.HALF_UP);
        this.diagonalSpeed = diagSpeed.doubleValue();

    }

    public void rotateLeft() {
        setRotation(getRotation() - BASE_ROTATE);
        if (getRotation() < 0) {
            setRotation(MAX_ROTATE - getRotation());
        }
    }

    public void rotateRight() {
        if (getRotation() >= (MAX_ROTATE - BASE_ROTATE)) {
            setRotation(0);
        } else {
            setRotation(getRotation() + BASE_ROTATE);
        }
    }

    public void moveForward(Map map) {
        double rotationDegrees = Math.toDegrees(getRotation() + HALF_PI) - 90;

        double coefficientX = Math.sin(rotationDegrees);
        double coefficientZ = Math.cos(rotationDegrees);

        double deltaX = coefficientX * getSpeed();
        double deltaZ = coefficientZ * getSpeed();

        changeX(deltaX, map);
        changeZ(deltaZ, map);
    }

    public void moveBackward() {

    }

    public void movementTick(Player player, Map map) {

        if (player.isKeyDown("A")) {
            rotateLeft();
        } else if (player.isKeyDown("D")) {
            rotateRight();
        }

        if (player.isKeyDown("W")) {
            moveForward(map);
        } else if (player.isKeyDown("S")) {
            moveBackward();
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

    //Check before changing...
    private void changeZ(double amt, Map map) {
        double newZ = getZ() + amt;
        if (map.isMovable(getX(), newZ + 1)) {
            setZ(newZ);
        } else if (map.isMovable(getX(), newZ)) {
            setZ(newZ);
        }

    }

    //Check before changing...
    private void changeX(double amt, Map map) {
        double newX = getX() + amt;
        if (map.isMovable(newX + 1, getZ())) {
            setX(newX);
        } else if (map.isMovable(newX, getZ())) {
            setX(newX);
        }

    }
}
