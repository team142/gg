/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.MessageKey;

/**
 *
 * @author just1689
 */
public class MessageKeyDown extends MessageKey {

    public MessageKeyDown() {
        super(ConversationType.P_KD);
    }

}