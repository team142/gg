package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;

public class MathUtils {

    public static double getRadiusFromElement(PlaceableElement element) {
        return element.getPoint().getRadius();
    }

    public static double getXFromElement(PlaceableElement element) {
        return element.getPoint().getX();
    }

    public static double getZFromElement(PlaceableElement element) {
        return element.getPoint().getZ();
    }

}
