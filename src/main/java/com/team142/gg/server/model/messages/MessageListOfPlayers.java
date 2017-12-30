/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import java.util.HashMap;

/**
 *
 * @author just1689
 */
public class MessageListOfPlayers extends Message {

    private final HashMap<String, Integer> PLAYERS = new HashMap<>();

    public MessageListOfPlayers(Game game) {
        this.setConversation(ConversationType.S_LIST_OF_PLAYERS.name());
        game.getPlayers().forEach((player) -> PLAYERS.put(player.getName(), player.getScore()));
    }

}
