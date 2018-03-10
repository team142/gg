/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.artificial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.GameMap;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.model.mappable.organic.SkinType;
import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.mappable.meta.MovableElement;
import com.team142.gg.server.utils.MathUtils;
import com.team142.gg.server.utils.PhysicsUtils;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class Bullet extends MovableElement {

    @JsonIgnore
    private Player player;

    private boolean ok;

    private double damage;

    public Bullet(Player player) {
        super(new SpaceTimePoint(player.getTANK().getPoint()), SkinType.BULLET.name(), Server.BULLET_DEFAULT_SPEED, -1);
        this.getPoint().setRotation(player.getTANK().getPoint().getRotation());
        this.player = player;
        this.damage = 35; //WHO KNOWs..
        this.ok = true;
        if (getPoint().getX() < 0) {
            getPoint().setX(0);
        }
        if (getPoint().getZ() < 0) {
            getPoint().setZ(0);
        }
        this.setWalkOnWater(true);

    }

    public boolean movementTickBullet() {
        if (!ok) {
            return ok; //False
        }

        double oldX = getPoint().getX();
        double oldZ = getPoint().getZ();


        GameMap map = (Repository.GAMES_ON_SERVER.get(player.getGameId()).getMap());
        boolean success = moveForward(map);
        if (!success) {
            ok = false;
            return ok;
        }



        double newX = getPoint().getX();
        double newZ = getPoint().getZ();

        Repository.GAMES_ON_SERVER
                .get(player.getGameId())
                .getTANKS()
                .values()
                .stream()
                .filter((tank) -> tank.getTAG() != player.getTAG())
                .filter((tank) -> PhysicsUtils.isWithinElementBoundaries(this.getPoint(), tank))
                .filter((tank) -> PhysicsUtils.isTinyObjectInLarger(tank.getPoint(), getPoint(), tank.getWidth()))
                .forEach(this::damage);

        if (!ok) {
            return false;
        }

        if(isBulletOutBounds(map)) {
            ok = false;
            return ok;
        }

        if (!Repository.GAMES_ON_SERVER.get(player.getGameId()).getMap().isShootover(getPoint().getX(), getPoint().getZ())) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Bullet is in something.. delete");
            ok = false;
        }
        return ok;

    }

    private boolean isBulletOutBounds(GameMap map) {
        if (getPoint().getX() < 0 || getPoint().getZ() < 0) {
            return true;
        }
        if (getPoint().getX() > map.getMaxX() + 1 || getPoint().getZ() > map.getMaxZ() + 1) {
            return true;
        }
        return false;
    }

    public void damage(Tank tank) {
        if (!ok) {
            return;
        }


        double angle = MathUtils.getAngleRadians(tank.getWidth() /2, tank.getDistanceToVertex());

        System.out.println("tank: " + tank.getPoint());
        System.out.println("rotation: " + tank.getPoint().getRotation());
        System.out.println(String.format("Front Left: [x: %f, z: %f]", getFrontLeftX(tank, angle), getFrontLeftZ(tank, angle)));
        System.out.println(String.format("Front Right: [x: %f, z: %f]", getFrontRightX(tank, angle), getFrontRightZ(tank, angle)));
        System.out.println(String.format("Back Left: [x: %f, z: %f]", getBackLeftX(tank, angle), getBackLeftZ(tank, angle)));
        System.out.println(String.format("Back Right: [x: %f, z: %f]", getBackRightX(tank, angle), getBackRightZ(tank, angle)));
        System.out.println("was inside of tank!");




        ok = false;
        BulletHitResult result = tank.damage(damage, player);

        Game game = Repository.GAMES_ON_SERVER.get(player.getGameId());
        game.getSoundManager().sendDing();
        Player toPlayer = Repository.PLAYERS_ON_SERVER.get(tank.getPlayerId());

        Logger.getLogger(this.getClass().getName())
                .log(
                        Level.INFO,
                        "{0} hit {1}",
                        new String[]{player.getName(), toPlayer.getName()}
                );
        
        GameManager.notifyHealthChange(game.getId(), tank);

        if (result.isLethal()) {
            GameManager.handleKill(game, player, toPlayer);
        }
    }

    //let a be direct distance (width) to point
    //let A be the angle that the point is away from origin
    //let b be length to point, perpendicular to origin
    //let B be angle opposite b

    //Formula: A = arcsin( (a * sin(B)) / b )
    //In our case, B is always 90, sin(90) = 1, so excluding from formula.
    //New formula: A = arcsin( a / b )

    public double getFrontLeftX(Tank tank, double angle) {
        double newRotation = getClockwiseRotation(tank.getPoint().getRotation(), angle);
        System.out.println("Tank point: " + tank.getPoint());
        return getNewX(tank.getPoint().getX(), newRotation, tank.getDistanceToVertex());
    }

    public double getFrontLeftZ(Tank tank, double angle) {
        double newRotation = getClockwiseRotation(tank.getPoint().getRotation(), angle);
        return getNewZ(tank.getPoint().getZ(), newRotation, tank.getDistanceToVertex());
    }

    public double getFrontRightX(Tank tank, double angle) {
        double newRotation = tank.getPoint().getRotation();
        if(newRotation  >= MAX_ROTATE - angle) {
            newRotation = 0;
        } else {
            newRotation = newRotation + angle;
        }
        return getNewX(tank.getPoint().getX(), newRotation, tank.getDistanceToVertex());
    }

    public double getFrontRightZ(Tank tank, double angle) {
        double newRotation = tank.getPoint().getRotation();
        if(newRotation  >= MAX_ROTATE - angle) {
            newRotation = 0;
        } else {
            newRotation = newRotation + angle;
        }
        return getNewZ(tank.getPoint().getZ(), newRotation, tank.getDistanceToVertex());
    }

    public double getBackLeftX(Tank tank, double angle) {
        double newRotation = tank.getPoint().getRotation() - Math.PI;
        if (newRotation < 0) {
            newRotation = MAX_ROTATE + newRotation;
        }
        if(newRotation  >= MAX_ROTATE - angle) {
            newRotation = 0;
        } else {
            newRotation = newRotation + angle;
        }
        return getNewX(tank.getPoint().getX(), newRotation, tank.getDistanceToVertex());
    }

    public double getBackLeftZ(Tank tank, double angle) {
        double newRotation = tank.getPoint().getRotation() - Math.PI;
        if (newRotation < 0) {
            newRotation = MAX_ROTATE + newRotation;
        }
        if(newRotation  >= MAX_ROTATE - angle) {
            newRotation = 0;
        } else {
            newRotation = newRotation + angle;
        }
        return getNewZ(tank.getPoint().getZ(), newRotation, tank.getDistanceToVertex());
    }

    public double getBackRightX(Tank tank, double angle) {
        double newRotation = tank.getPoint().getRotation() - Math.PI;
        if (newRotation < 0) {
            newRotation = MAX_ROTATE + newRotation;
        }
        newRotation = newRotation - angle;

        if(newRotation < 0) {
            newRotation = MAX_ROTATE - newRotation;
        }
        return getNewX(tank.getPoint().getX(), newRotation, tank.getDistanceToVertex());
    }

    public double getBackRightZ(Tank tank, double angle) {
        double newRotation = tank.getPoint().getRotation() - Math.PI;
        if (newRotation < 0) {
            newRotation = MAX_ROTATE + newRotation;
        }
        newRotation = newRotation - angle;
        if(newRotation < 0) {
            newRotation = MAX_ROTATE - newRotation;
        }
        return getNewZ(tank.getPoint().getZ(), newRotation, tank.getDistanceToVertex());
    }

    private boolean isIntersect(double point1x, double point1z, double point2x, double point2y, MovableElement object) {

        return false;
    }

    private double getClockwiseRotation(double orientation, double angle) {
        double newRotation = orientation - angle;
        if(newRotation < 0) {
            System.out.println("Old rotation: " + newRotation);//TODO May not need -1, just don't do MAX_ROTATE - rotate
            newRotation = (MAX_ROTATE - newRotation) * -1;
            System.out.println("New rotation: " + newRotation);
        }
        return newRotation;
    }

}
