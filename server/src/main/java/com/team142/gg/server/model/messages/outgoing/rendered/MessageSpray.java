/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.Player;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MessageSpray extends Message {

    @Getter
    private int tagId;

    @Getter
    private long ms;

    public MessageSpray(Player player, long ms) {
        this(player.getTAG(), ms);
    }

    public MessageSpray(int tagId, long ms) {
        this.setConversation(ConversationType.S_SHARE_SPRAY.name());
        this.tagId = tagId;
        this.ms = ms;
    }

}
