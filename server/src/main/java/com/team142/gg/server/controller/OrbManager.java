/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Map;
import com.team142.gg.server.model.Orb;
import com.team142.gg.server.model.messages.outgoing.rendered.MessageNewOrb;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author just1689
 */
public class OrbManager {
    
    public static void findRandomLocationForOrb(Orb orb, Map map) {
        boolean success = false;
        int x = 0;
        int z = 0;
        while (!success) {
            x = ThreadLocalRandom.current().nextInt(1, 48 + 1);
            z = ThreadLocalRandom.current().nextInt(1, 48 + 1);
            success = map.isMovable(x, z);
        }
        orb.setX(x);
        orb.setZ(z);
        
    }
    
    public static void possiblySpawnOrb(Game game) {
        spawnOrb(game);
    }
    
    public static void spawnOrb(Game game) {
        Orb orb = new Orb();
        orb.setName("orb" + game.getGAME_COUNTER().incrementAndGet());
        findRandomLocationForOrb(orb, game.getMap());
        game.getOrbs().put(orb.getName(), orb);
        MessageManager.sendPlayersAMessage(game, new MessageNewOrb(orb));
    }
    
}
