/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Item;
import com.team142.gg.server.model.messages.base.Message;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MessageShareThingsStatic extends Message {

    @Getter
    private final ArrayList<Item> THINGS = new ArrayList<>();

    public MessageShareThingsStatic() {
        setConversation(ConversationType.S_SHARE_STATIC_THINGS.name());
    }

}
