/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.Orb;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MessageNewOrb extends Message {

    @Getter
    private String name;

    @Getter
    private double x, z;

    public MessageNewOrb(Orb orb) {
        setConversation(ConversationType.S_ORB_N.name());
        this.name = orb.getName();
        this.x = orb.getPoint().getX();
        this.z = orb.getPoint().getZ();
    }

}
