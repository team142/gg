/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import com.team142.gg.server.controller.Referee;
import com.team142.gg.server.model.mappable.MapTileElement;
import com.team142.gg.server.model.mappable.MovableElement;
import com.team142.gg.server.model.messages.outgoing.other.MessageGameSummary;
import com.team142.gg.server.workers.TickerComms;
import com.team142.gg.server.workers.TickerPhysics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
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
    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private final String name;
    private final ConcurrentHashMap<String, MovableElement> TANKS = new ConcurrentHashMap<>();
    private final List<MapTileElement> MAP;
    private TickerComms tickerComms;
    private TickerPhysics tickerPhysics;

    public Game(String name) {
        this.MAP = Collections.synchronizedList(new ArrayList<>());
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public MessageGameSummary toGameSummary() {
        return new MessageGameSummary(id, name, players.size());
    }

    public boolean hasPlayer(String id) {
        return players.stream().anyMatch((player) -> (player.getId().equals(id)));
    }

    public void removePlayer(String id) {
        TANKS.remove(id);
        players.removeIf(player -> player.getId().equals(id));

    }

    public void playerJoins(Player player) {
        TANKS.put(player.getId(), player.getTANK());
        players.add(player);
        Referee.sendMapToPlayer(player.getId(), this);
        

    }

    public void start() {
        tickerPhysics = new TickerPhysics(this);
        new Thread(tickerPhysics).start();
        this.tickerComms = new TickerComms(this);
        new Thread(tickerComms).start();

    }

}
