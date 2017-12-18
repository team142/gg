/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import com.team142.gg.server.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author just1689
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageGameSummary {

    private String id;
    private String name;
    private int numberPlaying;

    public MessageGameSummary(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.numberPlaying = 1;
    }

}
