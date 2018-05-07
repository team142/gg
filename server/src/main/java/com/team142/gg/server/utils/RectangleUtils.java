package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.Rectangle;

public class RectangleUtils {

    public static double getFrontLeftX(Rectangle rectangle, double angle) {
        return getFrontLeftX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getFrontLeftX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = MathUtils.getAntiClockwiseRotation(rotation, angle);
        return MathUtils.getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getFrontLeftZ(Rectangle rectangle, double angle) {
        return getFrontLeftZ(rectangle.getPoint().getRotation(), rectangle.getPoint().getZ(), rectangle.getDistanceToVertex(), angle);
    }

    private static double getFrontLeftZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = MathUtils.getAntiClockwiseRotation(rotation, angle);
        return MathUtils.getNewZ(coord, newRotation, distanceToVertex);
    }

    public static double getFrontRightX(Rectangle rectangle, double angle) {
        return getFrontRightX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getFrontRightX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = rotation;
        newRotation = MathUtils.getClockwiseRotation(newRotation, angle);
        return MathUtils.getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getFrontRightZ(Rectangle rectangle, double angle) {
        return getFrontRightZ(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getZ(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getFrontRightZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = rotation;
        newRotation = MathUtils.getClockwiseRotation(newRotation, angle);
        return MathUtils.getNewZ(coord, newRotation, distanceToVertex);
    }

    public static double getBackLeftX(Rectangle rectangle, double angle) {
        return getBackLeftX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackLeftX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = MathUtils.getReversedRotation(rotation);
        newRotation = MathUtils.getClockwiseRotation(newRotation, angle);
        return MathUtils.getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getBackLeftZ(Rectangle rectangle, double angle) {
        return getBackLeftZ(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getZ(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackLeftZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = MathUtils.getReversedRotation(rotation);
        newRotation = MathUtils.getClockwiseRotation(newRotation, angle);
        return MathUtils.getNewZ(coord, newRotation, distanceToVertex);
    }

    public static double getBackRightX(Rectangle rectangle, double angle) {
        return getBackRightX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackRightX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = MathUtils.getReversedRotation(rotation);
        newRotation = MathUtils.getAntiClockwiseRotation(newRotation, angle);
        return MathUtils.getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getBackRightZ(Rectangle rectangle, double angle) {
        return getBackRightZ(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getZ(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackRightZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = MathUtils.getReversedRotation(rotation);
        newRotation = MathUtils.getAntiClockwiseRotation(newRotation, angle);
        return MathUtils.getNewZ(coord, newRotation, distanceToVertex);
    }

    public static boolean isIntersectRectangle(Rectangle rectangle, double startX, double startZ, double endX, double endZ) {
        double angle = MathUtils.getAngleRadians(rectangle.getWidth() / 2, rectangle.getDistanceToVertex());
        boolean doesIntersect;
        //Front
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                getFrontLeftX(rectangle, angle), getFrontLeftZ(rectangle, angle),
                getFrontRightX(rectangle, angle), getFrontRightZ(rectangle, angle));


        if (doesIntersect) {
            return true;
        }

        //Left
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                getFrontLeftX(rectangle, angle), getFrontLeftZ(rectangle, angle),
                getBackLeftX(rectangle, angle), getBackLeftZ(rectangle, angle));

        if (doesIntersect) {
            return true;
        }

        //Right
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                getFrontRightX(rectangle, angle), getFrontRightZ(rectangle, angle),
                getBackRightX(rectangle, angle), getBackRightZ(rectangle, angle));

        if (doesIntersect) {
            return true;
        }

        //Back
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                getBackLeftX(rectangle, angle), getBackLeftZ(rectangle, angle),
                getBackRightX(rectangle, angle), getBackRightZ(rectangle, angle));

        return doesIntersect;
    }
}
