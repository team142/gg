/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.base;

import lombok.Getter;

/**
 * @author just1689
 */
public enum SoundType {

    PEW("sounds/pew.mp3"),
    DING("sounds/ding.mp3"),
    EXPLODE("sounds/explode.mp3"),
    SHHHA("sounds/shhha.mp3"),
    X2("sounds/2x.mp3"),
    X3("sounds/3x.mp3"),
    X4("sounds/4x.mp3"),
    X5("sounds/5x.mp3"),
    YIPEE("sounds/yipee.mp3"),
    NIE("sounds/nie.mp3");

    @Getter
    private final String FILE;

    private SoundType(String FILE) {
        this.FILE = FILE;
    }

}
