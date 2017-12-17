/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class Game {

    private String id;
    private final List<Player> players = Collections.synchronizedList(new ArrayList<Player>());
    private Player owner;
    private final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>(40);

    public Game(Player owner) {
        this.id = UUID.randomUUID().toString();
        this.owner = owner;
        this.players.add(owner);

    }

    public boolean hasPlayer(String id) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
