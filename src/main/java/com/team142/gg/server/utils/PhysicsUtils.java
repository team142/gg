/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.PlaceableElement;

/**
 *
 * @author just1689
 */
public class PhysicsUtils {

    public static boolean isTinyObjectInLarger(PlaceableElement t, PlaceableElement l) {

        boolean isIn
                = ((t.getX() >= l.getX() && t.getX() <= l.getX() + 1)
                && (t.getZ() >= l.getZ() && t.getZ() <= l.getZ() + 1));
        if (isIn) {
            System.out.println("Tiny in in larger!");

        }
        return isIn;
    }

}
