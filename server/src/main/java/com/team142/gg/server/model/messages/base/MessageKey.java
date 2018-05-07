/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.base;

import lombok.Getter;

/**
 * @author just1689
 */
public class MessageKey extends Message {

    @Getter
    private String key;

    public MessageKey(ConversationType type) {
        setConversation(type.name());
    }

    public void setKey(String key) {
        this.key = key.toUpperCase();
    }

}
