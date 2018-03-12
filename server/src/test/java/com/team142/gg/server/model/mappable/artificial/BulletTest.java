package com.team142.gg.server.model.mappable.artificial;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.utils.MathUtils;
import com.team142.gg.server.utils.RectangleUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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

    @AfterAll
    static void finish() {
        System.out.println(BulletTest.class.getCanonicalName() +  ": Tests completed");
    }

    static SpaceTimePoint createSpaceTimePoint() {
        return new SpaceTimePoint(30, 29, 0.8, 0);
    }

    @Test
    void getFrontLeftX() {
        assertThat((float) RectangleUtils.getFrontLeftX(player.getTANK(), angle)).isEqualTo(29.85f);
    }

    @Test
    void getFrontLeftZ() {
        assertThat((float)RectangleUtils.getFrontLeftZ(player.getTANK(), angle)).isEqualTo(29.3f);
    }

    @Test
    void getFrontRightX() {
        assertThat((float)RectangleUtils.getFrontRightX(player.getTANK(), angle)).isEqualTo(30.15f);
    }

    @Test
    void getFrontRightZ() {
        assertThat((float)RectangleUtils.getFrontRightZ(player.getTANK(), angle)).isEqualTo(29.3f);
    }

    @Test
    void getBackLeftX() {
        assertThat((float)RectangleUtils.getBackLeftX(player.getTANK(), angle)).isEqualTo(29.85f);
    }

    @Test
    void getBackLeftZ() {
        assertThat((float)RectangleUtils.getBackLeftZ(player.getTANK(), angle)).isEqualTo(28.7f);
    }

    @Test
    void getBackRightX() {
        assertThat((float)RectangleUtils.getBackRightX(player.getTANK(), angle)).isEqualTo(30.15f);
    }

    @Test
    void getBackRightZ() {
        assertThat((float)RectangleUtils.getBackRightZ(player.getTANK(), angle)).isEqualTo(28.7f);
    }
}