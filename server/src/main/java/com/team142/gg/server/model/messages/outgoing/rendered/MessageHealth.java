/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.mappable.artificial.Tank;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class MessageHealth extends Message {

    private int tag;
    private double maxHealth, health;
    
    
    public MessageHealth(Tank tank) {
        setConversation(ConversationType.S_SHARE_HEALTH.name());
        this.tag = tank.getTAG();
        this.maxHealth = tank.getMaxHealth();
        this.health = tank.getHealth();
    
    }
    
    
    
}
