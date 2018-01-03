/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import java.math.BigDecimal;

/**
 *
 * @author just1689
 */
public class MathUtils {

    public static BigDecimal sqrt(BigDecimal value) {
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
        return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
    }

    public static boolean isModOf(BigDecimal first, BigDecimal by) {
        return (first.remainder(by).compareTo(BigDecimal.ZERO) == 0);
    }

}
