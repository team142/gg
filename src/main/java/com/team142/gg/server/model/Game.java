/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.controller.GameManager;
import com.team142.gg.server.controller.SoundManager;
import com.team142.gg.server.model.mappable.organic.TileBitmap;
import com.team142.gg.server.model.mappable.organic.MapTileElement;
import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.messages.outgoing.other.MessageGameSummary;
import com.team142.gg.server.model.messages.outgoing.other.MessagePlayerLeft;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;

/**
 *
 * @author just1689
 *
 * A game will be where game state is managed.
 */
@Data
public class Game {

    private final String id;
    private final List<Player> players = new CopyOnWriteArrayList<>();
    private final String name;
    private final ConcurrentHashMap<String, Tank> TANKS = new ConcurrentHashMap<>();
    private final List<MapTileElement> MAP;
    private TileBitmap[][] bitmap;
    private SoundManager soundManager;

    private final double startHealth;

    public Game(String name) {
        this.MAP = Collections.synchronizedList(new ArrayList<>());
        this.id = UUID.randomUUID().toString();
        this.soundManager = new SoundManager(this.id);
        this.name = name;
        this.startHealth = 100;
        startPinger();

    }

    public void spawn(Player player) {
        int x = ThreadLocalRandom.current().nextInt(1, 48 + 1);
        int z = ThreadLocalRandom.current().nextInt(1, 48 + 1);

        //TODO: check that not water or mountian
        player.getTANK().setHealth(startHealth);
        player.getTANK().setMaxHealth(startHealth);
        player.getTANK().setX(x);
        player.getTANK().setZ(z);

    }

    public MessageGameSummary toGameSummary() {
        return new MessageGameSummary(id, name, players.size());
    }

    public boolean hasPlayer(String id) {
        return players.stream().anyMatch((player) -> (player.getId().equals(id)));
    }

    public void removePlayer(Player player) {
        TANKS.remove(player.getId());
        MessageManager.sendPlayersAMessage(this, new MessagePlayerLeft(player.getTAG()));
        players.removeIf(playerItem -> playerItem.getId().equals(player.getId()));
        GameManager.sendScoreBoard(this);

    }

    public void playerJoins(Player player) {
        TANKS.put(player.getId(), player.getTANK());
        player.getTANK().setMaxHealth(startHealth);
        player.getTANK().setHealth(startHealth);
        players.add(player);
        player.start();
        spawn(player);

    }

    private void startPinger() {
        new Thread(() -> {
            while (true) {
                try {
                    getPlayers().forEach((player) -> player.getTickerComms().ping());
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

}
