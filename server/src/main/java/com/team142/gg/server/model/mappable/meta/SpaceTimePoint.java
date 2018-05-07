package com.team142.gg.server.model.mappable.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.team142.gg.server.model.jackson.DoubleContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SpaceTimePoint {

    @JsonIgnore
    public static final short X_COORD = 0;
    @JsonIgnore
    public static final short Y_COORD = 1;
    @JsonIgnore
    public static final short Z_COORD = 2;

    @Getter
    @Setter
    @JsonSerialize(using = DoubleContextualSerializer.class)
    private double x;

    @Getter
    @Setter
    @JsonSerialize(using = DoubleContextualSerializer.class)
    private double y = 0.16d;

    @Getter
    @Setter
    @JsonSerialize(using = DoubleContextualSerializer.class)
    private double z;

    @Getter
    @Setter
    private float rotation;

    @Getter
    @Setter
    private double radius;

    public SpaceTimePoint(double x, double z) {
        setX(x);
        setZ(z);
    }

    public SpaceTimePoint(double x, double z, double radius) {
        setX(x);
        setZ(z);
        setRadius(radius);
    }

    public SpaceTimePoint(double x, double z, double radius, float rotation) {
        setX(x);
        setZ(z);
        setRadius(radius);
        setRotation(rotation);
    }

    public SpaceTimePoint(SpaceTimePoint point) {
        setX(point.getX());
        setY(point.getY());
        setZ(point.getZ());
        setRadius(point.getRadius());
    }

    public String toString() {
        return "[x: " + getX() + ", z: " + getZ() + "] radius: " + getRadius() + ", rotation: " + getRotation();
    }
}
