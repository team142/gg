/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.other;

import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;

/**
 * @author just1689
 */
public class MessagePlayerLeft extends Message {

    @Getter
    private int tag;

    public MessagePlayerLeft(int tag) {
        setConversation(ConversationType.S_PLAYER_LEFT.name());
        this.tag = tag;
    }

}
