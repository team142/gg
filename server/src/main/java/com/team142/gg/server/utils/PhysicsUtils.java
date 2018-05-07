/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;

/**
 * @author just1689
 */
public class PhysicsUtils {

    public static boolean isTinyObjectInLarger(SpaceTimePoint t, SpaceTimePoint l, double width) {
        return ((t.getX() >= l.getX() - width
                && t.getX() <= l.getX() + width)
                && (t.getZ() >= l.getZ() - width
                && t.getZ() <= l.getZ() + width));
    }

    public static float getRadians(SpaceTimePoint viewer, SpaceTimePoint other) {
        //TODO: implement
        return 0;
    }

    public static boolean isWithinElementBoundaries(SpaceTimePoint point, PlaceableElement element) {
        return isWithinElementBoundaries(point.getX(), point.getZ(), element);
    }

    public static boolean isWithinElementBoundaries(double x, double z, PlaceableElement element) {
        return isXWithinRadius(x, element) && isZWithinRadius(z, element);
    }

    public static boolean isXWithinRadius(double x, PlaceableElement element) {
        if (x <= (MathUtils.getXFromElement(element) + MathUtils.getRadiusFromElement(element))) {
            if (x >= (MathUtils.getXFromElement(element) - MathUtils.getRadiusFromElement(element))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isZWithinRadius(double z, PlaceableElement element) {
        if (z <= MathUtils.getZFromElement(element) + MathUtils.getRadiusFromElement(element)) {
            if (z >= MathUtils.getZFromElement(element) - MathUtils.getRadiusFromElement(element)) {
                return true;
            }
        }
        return false;
    }

}
