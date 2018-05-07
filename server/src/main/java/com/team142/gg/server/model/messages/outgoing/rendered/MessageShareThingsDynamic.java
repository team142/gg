/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;

import java.util.ArrayList;

/**
 * @author just1689
 */
@JsonInclude(Include.NON_NULL)
public class MessageShareThingsDynamic extends Message {

    @Getter
    private final ArrayList<PlaceableElement> things = new ArrayList<>();

    public MessageShareThingsDynamic(Game game) {
        setConversation(ConversationType.S_SHARE_DYNAMIC_THINGS.name());
        this.things.addAll(game.getTANKS().values());
    }

}
