/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.other;

import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;
import lombok.Setter;

/**
 * @author just1689
 */
public class MessageIntelChange extends Message {

    @Getter
    @Setter
    private boolean on;

    public MessageIntelChange(boolean on) {
        setConversation(ConversationType.S_SHARE_INTEL.name());
        setOn(on);
    }

}
