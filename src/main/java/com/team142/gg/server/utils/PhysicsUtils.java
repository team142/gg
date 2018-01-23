/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;

/**
 *
 * @author just1689
 */
public class PhysicsUtils {

    public static boolean isTinyObjectInLarger(PlaceableElement t, PlaceableElement l) {

        boolean isIn
                = ((t.getX() >= l.getX() - 0.5 && t.getX() <= l.getX() + 0.5)
                && (t.getZ() >= l.getZ() - 0.5 && t.getZ() <= l.getZ() + 0.5));
        if (isIn) {
            System.out.println("Tiny in in larger!");

        }
        return isIn;
    }

}
