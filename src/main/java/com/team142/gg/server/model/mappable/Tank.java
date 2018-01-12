/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

/**
 *
 * @author just1689
 */
public class Tank extends MovableElement {

    private double health;
    private double maxHealth;

    public Tank(double x, double y, double z, String skin, double speed, int tag, double hp) {
        super(x, y, z, skin, speed, tag);
        this.health = hp;
        this.maxHealth = hp;
    }

}
