/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.mappable.Bullet;
import com.team142.gg.server.model.messages.base.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
@AllArgsConstructor
public class MessageBullet extends Message {

    private Bullet BULLET;

}
