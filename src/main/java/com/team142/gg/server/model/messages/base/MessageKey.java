/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.base;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class MessageKey extends Message {

    @Setter
    @Getter
    private String key;

    public MessageKey(ConversationType type) {
        setConversation(type.name());
    }

}
