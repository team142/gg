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
        if(getRotation() <= BASE_ROTATE) {
            setRotation(getRotation() - BASE_ROTATE);
        } else {
            setRotation(MAX_ROTATE);
        }
    }
    
    public void rotateRight() {
        if(getRotation() >= (MAX_ROTATE - BASE_ROTATE)) {
            setRotation(0);
        } else {
            setRotation(getRotation() + BASE_ROTATE);
        }
    }
    
    public void moveForward() {
        
    }
    
    public void moveBackward() {
        
    }

    public void movementTick(Player player, Map map) {
        
        if(player.isKeyDown("a")) {
            rotateLeft();
        } else if(player.isKeyDown("d")) {
            rotateRight();
        }
        
        if(player.isKeyDown("w")) {
            moveForward();
        } else if(player.isKeyDown("d")) {
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
    private void changeZ(double amt, int dir, Map map) {
        double newZ = getZ() + (amt * dir);
        if (dir == 1 && map.isMovable(getX(), newZ + 1)) {
            setZ(newZ);
        } else if (dir == -1 && map.isMovable(getX(), newZ)) {
            setZ(newZ);
        }

    }

    //Check before changing...
    private void changeX(double amt, int dir, Map map) {
        double newX = getX() + (amt * dir);
        if (dir == 1 && map.isMovable(newX + 1, getZ())) {
            setX(newX);
        } else if (dir == -1 && map.isMovable(newX, getZ())) {
            setX(newX);
        }

    }
}
