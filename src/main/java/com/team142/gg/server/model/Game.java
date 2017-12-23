/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import lombok.Value;

/**
 *
 * @author just1689
 *
 * A game will be where game state is managed.
 */
@Value
public class Game {

    private final String id;
    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>(40);
    private final String name;

    public Game(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;

    }

    public boolean hasPlayer(String id) {
        return players.stream().anyMatch((player) -> (player.getId().equals(id)));
    }

    public void removePlayer(String id) {
        players.removeIf(player -> player.getId().equals(id));

    }

    public void playerJoins(Player player) {
        //TODO: announce

        players.add(player);

    }

}
