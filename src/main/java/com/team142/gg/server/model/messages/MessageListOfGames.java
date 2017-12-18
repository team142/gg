/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class MessageListOfGames extends Message {
    
    private ArrayList<MessageGameSummary> games;
    
    public MessageListOfGames() {
        this.games = new ArrayList<>();
        setConversation(ConversationType.S_ANSWER_LIST_OF_GAMES.name());
    }
    
}
