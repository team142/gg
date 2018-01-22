/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.mappable.organic.MapTileElement;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MessageShareMap extends Message {

    @Getter
    private final List<MapTileElement> MAP;

    public MessageShareMap(Game game) {
        setConversation(ConversationType.S_SHARE_MAP.name());
        this.MAP = game.getMAP();

    }

}
