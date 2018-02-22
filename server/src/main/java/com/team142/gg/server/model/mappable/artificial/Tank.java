/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.artificial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.mappable.meta.MovableElement;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class Tank extends MovableElement {

    @Getter
    @Setter
    private double health;

    @Getter
    @Setter
    private double maxHealth;

    @Getter
    @Setter
    @JsonIgnore
    private String playerId;

    @Getter
    @Setter
    @JsonIgnore
    private double width = 0.3d;

    public Tank(SpaceTimePoint point, String skin, double speed, int tag, double hp, Player player) {
        super(point, skin, speed, tag);
        this.health = hp;
        this.maxHealth = hp;
        this.playerId = player.getId();
    }

    public BulletHitResult damage(double dmg, Player fromPlayer) {
        health -= dmg;
        return new BulletHitResult(true, dmg, health <= 0);

    }

    public void heal(int inc) {
        setHealth(getHealth() + inc);
        checkNotTooHealthy();
    }

    private void checkNotTooHealthy() {
        if (getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
    }

}
