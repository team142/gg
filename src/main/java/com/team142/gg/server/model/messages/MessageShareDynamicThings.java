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
public class MessageShareDynamicThings extends Message {

    @Getter
    private final ArrayList<Item> things = new ArrayList<>();

    public MessageShareDynamicThings() {
        setConversation(ConversationType.S_SHARE_DYNAMIC_THINGS.name());
    }

}
