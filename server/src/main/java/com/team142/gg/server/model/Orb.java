/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class Orb {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private double x, z;

    @Getter
    private String gameId;

    public Orb(String gameId) {
        this.gameId = gameId;
    }

    public void use() {
        //Ummm
    }

}
