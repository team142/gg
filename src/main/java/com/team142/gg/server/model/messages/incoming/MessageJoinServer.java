/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.incoming;

import com.team142.gg.server.controller.ServerAdmin;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author just1689
 */
@NoArgsConstructor
public class MessageJoinServer extends Message implements Runnable {

    @Setter
    @Getter
    private String name;

    @Override
    public void run() {
        ServerAdmin.handle(this);

    }

}
