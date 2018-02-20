/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;

/**
 *
 * @author just1689
 */
public class PhysicsUtils {

    public static boolean isTinyObjectInLarger(SpaceTimePoint t, SpaceTimePoint l, double width) {

        return ((t.getX() >= l.getX() - width
                && t.getX() <= l.getX() + width)
                && (t.getZ() >= l.getZ() - width
                && t.getZ() <= l.getZ() + width));
    }

}
