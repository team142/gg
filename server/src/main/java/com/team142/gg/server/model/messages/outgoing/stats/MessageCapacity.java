/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.stats;

import com.team142.gg.server.model.Server;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class MessageCapacity {

    private String friendlyName;
    private String joinUrl;
    private int currentPlayers;
    private int maxPlayers;

    public MessageCapacity() {
        this.friendlyName = Server.SERVER_NAME;
        this.joinUrl = Server.SERVER_NAME;
        this.currentPlayers = Server.countPlayers();
        this.maxPlayers = Server.MAX_PLAYERS_ON_SERVER;
        
    }

}
