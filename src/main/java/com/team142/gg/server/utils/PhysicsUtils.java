/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class PhysicsUtils {

    private static final Logger LOG = Logger.getLogger(PhysicsUtils.class.getName());

    public static boolean isTinyObjectInLarger(PlaceableElement t, PlaceableElement l, double width) {

        boolean isIn
                = ((t.getX() >= l.getX() - width && t.getX() <= l.getX() + width)
                && (t.getZ() >= l.getZ() - width && t.getZ() <= l.getZ() + width));
        if (isIn) {
            LOG.log(Level.INFO, "Tiny is in larger");
        }
        return isIn;
    }

}
