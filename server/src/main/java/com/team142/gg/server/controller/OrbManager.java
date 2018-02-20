/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.GameMap;
import com.team142.gg.server.model.Orb;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageDeleteOrb;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageNewOrb;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author just1689
 */
public class OrbManager {

    public static void findRandomLocationForOrb(Orb orb, GameMap map) {
        boolean success = false;
        int x = 0;
        int z = 0;
        while (!success) {
            x = ThreadLocalRandom.current().nextInt(1, 48 + 1);
            z = ThreadLocalRandom.current().nextInt(1, 48 + 1);
            success = map.isMovable(x, z);
        }
        orb.getPoint().setX(x);
        orb.getPoint().setZ(z);

    }

    public static void possiblySpawnOrb(Game game) {
        spawnOrb(game);
    }

    public static void spawnOrb(Game game) {
        int nextGameCounter = game.getGAME_COUNTER().incrementAndGet();
        SpaceTimePoint point = new SpaceTimePoint(0, 0);
        Orb orb = new Orb("orb" + nextGameCounter, game.getId(), point, "orb", nextGameCounter);
        findRandomLocationForOrb(orb, game.getMap());
        game.getOrbs().put(orb.getName(), orb);
        MessageManager.sendPlayersAMessage(game, new MessageNewOrb(orb));
    }

    public static Orb isTankInOrb(Tank TANK, String gameId) {
        int x = (int) TANK.getPoint().getX();
        int z = (int) TANK.getPoint().getZ();
        return Repository.GAMES_ON_SERVER.get(gameId)
                .getOrbs()
                .values()
                .stream()
                .filter((orb) -> x == orb.getPoint().getX() && z == orb.getPoint().getZ())
                .findFirst()
                .orElse(null);
    }

    public static void remove(Orb orb) {
        Game game = Repository.GAMES_ON_SERVER.get(orb.getGameId());
        game.getOrbs().remove(orb.getName());
        MessageManager.sendPlayersAMessage(game, new MessageDeleteOrb(orb));
    }

}
