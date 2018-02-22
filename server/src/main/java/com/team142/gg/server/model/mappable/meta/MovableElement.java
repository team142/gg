/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.model.Map;
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

    public boolean moveForward(Map map) {
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

    public boolean moveBackward(Map map) {

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

    public void movementTick(Map map) {

        if (getPoint().getX() < 0) {
            getPoint().setX(0);
        }
        if (getPoint().getX() > 49 + 1) {
            getPoint().setX(49);
        }
        if (getPoint().getZ() < 0) {
            getPoint().setZ(0);
        }
        if (getPoint().getZ() > 49 + 1) {
            getPoint().setZ(49);
        }

    }

    private boolean isShootoverValid(double amt, double x, double z, Map map, short coordinateType) {
        if(coordinateType == SpaceTimePoint.Z_COORD) {
            if((amt > 0) && (map.isShootover(x, z + 1))) {
                return true;
            } else if((amt < 0 && map.isShootover(x, z))) {
                return true;
            } else {
                return false;
            }
        } else if(coordinateType == SpaceTimePoint.X_COORD) {
            if (amt > 0 && map.isShootover(x + 1, z)) {
                return true;
            } else if (amt < 0 && map.isShootover(x, z)) {
                return true;
            } else {
                //Failed to move
                return false;
            }
        }
        return false;
    }

    private boolean isMovementValid(double amt, double x, double z, Map map, short coordinateType) {
        if(coordinateType == SpaceTimePoint.X_COORD) {
            if (amt > 0 && map.isMovable(x + 1, z)) {
                return true;
            } else if (amt < 0 && map.isMovable(x, z)) {
                return true;
            } else {
                //Failed tp move
                return false;
            }
        }

        return false;
    }

    //Check before changing...
    private boolean changeZ(double amt, Map map) {
        double newZ = getPoint().getZ() + amt;
        if (isWalkOnWater()) {
            if(isShootoverValid(amt, getPoint().getX(), newZ, map, SpaceTimePoint.Z_COORD)) {
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
    private boolean changeX(double amt, Map map) {
        double newX = getPoint().getX() + amt;
        if (isWalkOnWater()) {

            if(isShootoverValid(amt, newX, getPoint().getZ(), map, SpaceTimePoint.X_COORD)) {
                getPoint().setX(newX);
                return true;
            } else {
                //Failed to move
                return false;
            }
        }

        if(isMovementValid(amt, newX, getPoint().getZ(), map, SpaceTimePoint.X_COORD)) {
            getPoint().setX(newX);
            return true;
        } else {
            //Failed to move
            return false;
        }
    }
}
