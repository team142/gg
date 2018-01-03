/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author just1689
 */
public class DirectionTypes {

    public static final BigDecimal ONE_TICK_ROTATE = new BigDecimal(0.785);

    public static final BigDecimal DIR0 = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR1 = ONE_TICK_ROTATE.setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR2 = DIR1.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR3 = DIR2.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR4 = DIR3.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR5 = DIR4.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR6 = DIR5.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR7 = DIR6.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);
    public static final BigDecimal DIR8 = DIR7.add(ONE_TICK_ROTATE).setScale(3, RoundingMode.HALF_UP);

}
