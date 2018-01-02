/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages.base;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
@Data
public class Item {

    private String skin;

    private BigDecimal x, y, z;

    private int rotation;

}
