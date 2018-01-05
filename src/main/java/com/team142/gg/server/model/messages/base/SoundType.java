/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.base;

import lombok.Getter;

/**
 *
 * @author just1689
 */
public enum SoundType {

    PEW("sounds/pew.mp3");

    @Getter
    private final String FILE;

    private SoundType(String FILE) {
        this.FILE = FILE;
    }

}
