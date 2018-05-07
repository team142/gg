package com.team142.gg.server.model.mappable.meta;

public interface Rectangle {
    double width = 0;
    double height = 0;
    double distanceToVertex = 0;

    double getWidth();

    double getHeight();

    double calculateDistanceToVertex();

    double getDistanceToVertex();

    SpaceTimePoint getPoint();

}
