package com.team142.gg.server.model.mappable.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SpaceTimePoint {

    @Getter
    @Setter
    private double x;

    @Getter
    @Setter
    private double y = 0.16d;

    @Getter
    @Setter
    private double z;

    @Getter
    @Setter
    private float rotation;

    public SpaceTimePoint(double x, double z) {
        setX(x);
        setZ(z);
    }

    public SpaceTimePoint(SpaceTimePoint point) {
        setX(point.getX());
        setY(point.getY());
        setZ(point.getZ());
    }
}
