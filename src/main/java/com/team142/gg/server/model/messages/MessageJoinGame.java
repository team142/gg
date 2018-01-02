/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import com.team142.gg.server.controller.Referee;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author just1689
 */
@Data
@NoArgsConstructor
public class MessageJoinGame extends Message implements Runnable {

    private String id;

    @Override
    public void run() {
        Referee.handle(getFrom(), this);

    }

}
