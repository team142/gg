/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.model.GameMap;
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

    @JsonIgnore
    @Getter
    @Setter
    private boolean walkOnWater;

    public static final float BASE_ROTATE = (float) Math.toRadians(1.25);
    public static final float MAX_ROTATE = (float) (Math.PI * 2);
    public static final float HALF_PI = (float) (Math.PI / 2);

    public MovableElement(SpaceTimePoint point, String skin, double speed, int tag) {
        super(point, skin, tag);
        this.speed = speed;
    }

    public void rotateLeft() {
        getPoint().setRotation(getPoint().getRotation() - BASE_ROTATE);
        if (getPoint().getRotation() < 0) {
            getPoint().setRotation(MAX_ROTATE - getPoint().getRotation());
        }
    }

    public void rotateRight() {
        if (getPoint().getRotation() >= (MAX_ROTATE - BASE_ROTATE)) {
            getPoint().setRotation(0);
        } else {
            getPoint().setRotation(getPoint().getRotation() + BASE_ROTATE);
        }
    }

    public boolean moveForward(GameMap map) {
        double coefficientX = Math.sin(getPoint().getRotation());
        double coefficientZ = Math.cos(getPoint().getRotation());
        if (!changeX(coefficientX * getSpeed(), map)) {
            return false;
        }
        if (!changeZ(coefficientZ * getSpeed(), map)) {
            return false;
        }
        return true;
    }

    public boolean moveBackward(GameMap map) {

        double newRotation = getPoint().getRotation();
        newRotation = newRotation - Math.PI;

        if (newRotation < 0) {
            newRotation = MAX_ROTATE + newRotation;
        }

        double coefficientX = Math.sin(newRotation);
        double coefficientZ = Math.cos(newRotation);

        if (!changeX(coefficientX * getSpeed(), map)) {
            return false;
        }
        if (!changeZ(coefficientZ * getSpeed(), map)) {
            return false;
        }
        return true;

    }

    public void movementTick(GameMap map) {

        if (getPoint().getX() < 0) {
            getPoint().setX(0);
        }
        if (getPoint().getX() > (map.getX() - 1) + 1) {
            getPoint().setX(map.getX() - 1);
        }
        if (getPoint().getZ() < 0) {
            getPoint().setZ(0);
        }
        if (getPoint().getZ() > (map.getZ() - 1) + 1) {
            getPoint().setZ(map.getZ() - 1);
        }

    }

    //Check before changing...
    private boolean changeZ(double amt, GameMap map) {
        double newZ = getPoint().getZ() + amt;
        if (isWalkOnWater()) {
            if (amt > 0 && map.isShootover(getPoint().getX(), newZ + 1)) {
                getPoint().setZ(newZ);
            } else if (amt < 0 && map.isShootover(getPoint().getX(), newZ)) {
                getPoint().setZ(newZ);

            } else {
                //Failed to move
                return false;
            }
            return true;
        }

        if (amt > 0 && map.isMovable(getPoint().getX(), newZ + 1)) {
            getPoint().setZ(newZ);
        } else if (amt < 0 && map.isMovable(getPoint().getX(), newZ)) {
            getPoint().setZ(newZ);
        } else {
            //Failed to move
            return false;
        }
        return true;

    }

    //Check before changing...
    private boolean changeX(double amt, GameMap map) {
        double newX = getPoint().getX() + amt;
        if (isWalkOnWater()) {
            if (amt > 0 && map.isShootover(newX + 1, getPoint().getZ())) {
                getPoint().setX(newX);
            } else if (amt < 0 && map.isShootover(newX, getPoint().getZ())) {
                getPoint().setX(newX);
            } else {
                //Failed to move
                return false;
            }
            return true;
        }

        if (amt > 0 && map.isMovable(newX + 1, getPoint().getZ())) {
            getPoint().setX(newX);
        } else if (amt < 0 && map.isMovable(newX, getPoint().getZ())) {
            getPoint().setX(newX);
        } else {
            //Failed tp move
            return false;
        }
        return true;

    }
}
