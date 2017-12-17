/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server;

import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class Player {

    private String id;
    private String name;

    public Player(String id) {
        this.id = id;
    }

}
