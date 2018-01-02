/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
public class Placeable {

    @Getter
    @Setter
    private BigDecimal x, y, z;

    @Getter
    @Setter
    private String skin;

    @Getter
    @Setter
    private int rotation;

}
