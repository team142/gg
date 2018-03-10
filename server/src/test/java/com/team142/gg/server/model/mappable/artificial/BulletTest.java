package com.team142.gg.server.model.mappable.artificial;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.utils.MathUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    static Bullet bullet;
    static Player player;
    static double angle;

    @BeforeAll
    static void setup() {
        SpaceTimePoint point = createSpaceTimePoint();
        player = new Player("TestPlayerID");
        player.getTANK().setPoint(point);
        bullet = new Bullet(player);
        angle = MathUtils.getAngleRadians(player.getTANK().getWidth() /2, player.getTANK().getDistanceToVertex());
    }

    static SpaceTimePoint createSpaceTimePoint() {
        return new SpaceTimePoint(30, 29, 0.8, 0);
    }

    @Test
    void getFrontLeftX() {
        assertTrue((float)MathUtils.getFrontLeftX(player.getTANK(), angle) == 29.85f);
    }

    @Test
    void getFrontLeftZ() {
        assertTrue((float)MathUtils.getFrontLeftZ(player.getTANK(), angle) == 29.3f);
    }

    @Test
    void getFrontRightX() {
        assertTrue((float)MathUtils.getFrontRightX(player.getTANK(), angle) == 30.15f);
    }

    @Test
    void getFrontRightZ() {
        assertTrue((float)MathUtils.getFrontRightZ(player.getTANK(), angle) == 29.3f);
    }

    @Test
    void getBackLeftX() {
        assertTrue((float)MathUtils.getBackLeftX(player.getTANK(), angle) == 29.85f);
    }

    @Test
    void getBackLeftZ() {
        assertTrue((float)MathUtils.getBackLeftZ(player.getTANK(), angle) == 28.7f);
    }

    @Test
    void getBackRightX() {
        assertTrue((float)MathUtils.getBackRightX(player.getTANK(), angle) == 30.15f);
    }

    @Test
    void getBackRightZ() {
        assertTrue((float)MathUtils.getBackRightZ(player.getTANK(), angle) == 28.7f);
    }
}