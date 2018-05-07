/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.messages.base.ConversationType;
import lombok.Getter;

/**
 * @author just1689
 */
public class MessageRadar extends MessageShareThingsDynamic {

    @Getter
    private final int timeout;

    public MessageRadar(Game game, int timeout) {
        super(game);
        this.timeout = timeout;
        setConversation(ConversationType.S_SHARE_RADAR.name());
    }

}
