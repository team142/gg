/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.controller.runnable.powers.Power;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;

/**
 *
 * @author just1689
 */
public class MessagePowerLevel extends Message {

    @Getter
    private String power;

    @Getter
    private int level;

    public MessagePowerLevel(Power power) {
        this.power = String.valueOf(power.getID());
        this.level = power.getLevel();
    }

}
