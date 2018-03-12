package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import com.team142.gg.server.model.mappable.meta.Rectangle;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;

import java.util.Arrays;
import java.util.Comparator;

import static com.team142.gg.server.model.mappable.meta.MovableElement.MAX_ROTATE;

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

    public static double getGradientOfLine(SpaceTimePoint pointA, SpaceTimePoint pointB) {
        return getGradientOfLine(pointA.getX(), pointA.getZ(), pointB.getX(), pointB.getZ());
    }

    private static double getGradientOfLine(double x1, double z1, double x2, double z2) {
        return ((z2-z1)/(x2-x1));
    }

    private static double getYIntersectOfLine(double x1, double z1, double gradient) {
        double c = z1 - (gradient*x1);
        return c;
    }

    public static double getYIntersectOfLine(double x1, double z1, double x2, double z2) {
        double gradient = getGradientOfLine(x1, z1, x2, z2);
        return getYIntersectOfLine(x1, z1, gradient);
    }

    public static double getYIntersectOfLine(SpaceTimePoint point, double gradient) {
        return getYIntersectOfLine(point.getX(), point.getZ(), gradient);
    }


    /**
     * quadratic equation -
     * https://math.stackexchange.com/questions/228841/how-do-i-calculate-the-intersections-of-a-straight-line-and-a-circle
     *
     * For line segments: https://math.stackexchange.com/a/275537
     */
    public static boolean doesIntersectCircle(double x1, double z1, double x2, double z2, PlaceableElement element) {

        x1 -= MathUtils.getXFromElement(element);
        z1 -= MathUtils.getZFromElement(element);
        x2 -= MathUtils.getXFromElement(element);
        z2 -= MathUtils.getZFromElement(element);

        //Matrices magic - need to learn more about these and vectors.
        double a = Math.pow(x1, 2) + Math.pow(z1, 2) - Math.pow(MathUtils.getRadiusFromElement(element), 2);
        double b = 2 * (x1 * (x2 - x1) + z1 * (z2 - z1));
        double c = Math.pow((x2 - x1), 2) + Math.pow((z2 - z1), 2);

        // Discriminant is the portion under the square root of the quadratic equation.
        // If the discriminant is < 0, the line misses the circle. If it is == 0, the line is tangent/intersects once.
        // If the line is > 0, the line intersects twice.
        double discriminant = Math.pow(b, 2) - 4 * a * c;
        if(discriminant < 0) return false;
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = (-b + sqrtDiscriminant) / (2 * a);
        double t2 = (-b - sqrtDiscriminant) / (2 * a);
        return (0 < t1 && t1 < 1) || (0 < t2 && t2 < 1);
    }

    private static boolean isLinesParallel(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                    double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {
        double slopeLine1, slopeLine2;
        slopeLine1 = (line1EndZ - line1StartZ) / (line1EndX - line1StartX);
        slopeLine2 = (line2EndZ - line2StartZ) / (line2EndX - line2StartX);
        return slopeLine1 == slopeLine2;
    }

    private static boolean isCollinearLinesIntersect(double start, double end, double pointChecking) {
        if(end == pointChecking) {
            //Point is at the end of the line
            return true;
        }
        //Lines are not collinear, should never have gotten here. Check the logic before here.
        //Returns true if the checked point start fits along the line, otherwise the lines were not collinear
        return pointChecking >= start && pointChecking <= end;
    }

    /**
     * Checks if vertical lines intersects, using the Z values
     *
     * Do not use this method without checking for parallel and collinear lines using isCollinearAndTouching first,
     * should only be used on lines with an undefined gradient.
     *
     * @param line1StartZ Z-coordinate of line 1 start point
     * @param line1EndZ Z-coordinate of line 1 end point
     * @param line2StartZ Z-coordinate of line 2 start point
     * @param line2EndZ Z-coordinate of line 2 end point
     *
     * @return true if two vertical line segments intersect
     */
    private static boolean isVerticalLinesIntersect(double line1StartZ, double line1EndZ, double line2StartZ, double line2EndZ) {
        double line1Start = Math.min(line1StartZ, line1EndZ);
        double line1End = Math.max(line1StartZ, line1EndZ);

        double line2Start = Math.min(line2StartZ, line2EndZ);
        double line2End = Math.max(line2StartZ, line2EndZ);

        if(line1Start == line2Start) {
            //line1 and line2 share start point
            return true;
        } else if(line1Start < line2Start) {
            //Line1 lower
            //line2 starts along line1
            return (line2Start >= line1Start) && (line2Start <= line1End);
        } else if(line1Start > line2Start) {
            //Line2 lower
            //returns true if line1 starts along line2
            return (line1Start >= line2Start) && (line1Start <= line2End);
        }
        return false;
    }

    /**
     * Checks if a parallel line is collinear and whether it overlaps or touches
     *
     * Do not use this method without checking for parallel lines using isLinesParallel first
     *
     * @param line1StartX X-coordinate of line 1 start point
     * @param line1StartZ Z-coordinate of line 1 start point
     * @param line1EndX X-coordinate of line 1 end point
     * @param line1EndZ Z-coordinate of line 1 end point
     * @param line2StartX X-coordinate of line 2 start point
     * @param line2StartZ Z-coordinate of line 2 start point
     * @param line2EndX X-coordinate of line 2 end point
     * @param line2EndZ Z-coordinate of line 2 end point
     *
     * @return true if two lines are collinear and touch or overlap
     */
    private static boolean isCollinearAndTouching(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                           double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {

        double slopeLine1, slopeLine2;

        //(delta y)/(delta x)
        //delta x
        double slopeDenominator = (line1EndX - line1StartX);
        if (slopeDenominator == 0) {
            //Line is vertical! No change in x
            return line1EndX == line2StartX && isVerticalLinesIntersect(line1StartZ, line1EndZ, line2StartZ, line2EndZ);
        } else {
            slopeLine1 = (line1EndZ - line1StartZ) / slopeDenominator;
            slopeLine1 = (Math.round(slopeLine1 * 10000));
            slopeLine1 = slopeLine1 / 10000;
        }

        //delta x
        slopeDenominator = (line2EndX - line2StartX);
        if (slopeDenominator == 0) {
            //Line is vertical! No change in x
            return line1EndX == line2StartX && isVerticalLinesIntersect(line1StartZ, line1EndZ, line2StartZ, line2EndZ);
        } else {
            slopeLine2 = (line2EndZ - line2StartZ) / slopeDenominator;
            slopeLine2 = (Math.round(slopeLine2 * 10000));
            slopeLine2 = slopeLine2 / 10000;
        }

        if (!(slopeLine1 == slopeLine2)) {
            //Lines are not parallel, shouldn't have got into this method in the first place.
            return false;
        }

        double zInterceptLine1 = -(slopeLine1 * line1StartX) + line1StartZ;
        double zInterceptLine2 = -(slopeLine2 * line2StartX) + line2StartZ;

        if (!(zInterceptLine1 == zInterceptLine2)) {
            //Lines don't share a z-intercept, do not have the same equation, lines are not collinear.
            //Shouldn't have gotten into this method in the first place.
            return false;
        }

        double[][] line1 = new double[2][2];
        line1[0][0] = line1StartX;
        line1[0][1] = line1StartZ;
        line1[1][0] = line1EndX;
        line1[1][1] = line1EndZ;
        Arrays.sort(line1, Comparator.comparingDouble(a -> a[0]));

        double[][] line2 = new double[2][2];
        line2[0][0] = line2StartX;
        line2[0][1] = line2StartZ;
        line2[1][0] = line2EndX;
        line2[1][1] = line2EndZ;
        Arrays.sort(line2, Comparator.comparingDouble(a -> a[0]));

        if (line1[0][0] == line2[0][0]) {
            //Lines share a start coordinate
            return true;
        } else if (line1[1][0] == line2[1][0]){
            //lines share end coordinate
            return true;
        } else if (line1[0][0] < line2[0][0]) {
            return isCollinearLinesIntersect(line1[0][0], line1[1][0], line2[0][0]);
        } else
            //Lines are not collinear, should never have gotten here. Check the logic before here.
            return line1[0][0] > line2[0][0] && isCollinearLinesIntersect(line2[0][0], line2[1][0], line1[0][0]);


    }

    private static boolean isLinesIntersect(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                            double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {

        double line1LengthX = line1EndX - line1StartX;
        double line1LengthY = line1EndZ - line1StartZ;
        double line2LengthX = line2EndX - line2StartX;
        double line2LengthY = line2EndZ - line2StartZ;

        double sDenominator = (-line2LengthX * line1LengthY + line1LengthX * line2LengthY);
        if(sDenominator == 0) {
            //check if parallel
            //check if collinear
            return isLinesParallel(
                    line1StartX, line1StartZ,
                    line1EndX, line1EndZ,
                    line2StartX, line2StartZ,
                    line2EndX, line2EndZ) &&
                    isCollinearAndTouching(
                            line1StartX, line1StartZ,
                            line1EndX, line1EndZ,
                            line2StartX, line2StartZ,
                            line2EndX, line2EndZ);
        }

        double s, t;
        double tDenominator = ((-line2LengthX * line1LengthY) + (line1LengthX * line2LengthY));
        double sNumerator = ((-line1LengthY * (line1StartX - line2StartX)) + (line1LengthX * (line1StartZ - line2StartZ)));
        double tNumerator = ((line2LengthX * (line1StartZ - line2StartZ)) - (line2LengthY * (line1StartX - line2StartX)));

        s = sNumerator / sDenominator;
        t = tNumerator / tDenominator;

        // Return true if collision detected
        return s >= 0 && s <= 1 && t >= 0 && t <= 1;
    }

    //let a be direct distance (width) to point
    //let A be the angle that the point is away from origin
    //let b be length to point, directly
    //let B be angle opposite b

    //Formula: A = arcsin( (a * sin(B)) / b )
    //In our case, B is always 90, sin(90) = 1, so excluding from formula.
    //New formula: A = arcsin( a / b )

    public static double getAngleRadians(double a, double b) {
        return Math.asin(a/b);
    }

    public static double getFrontLeftX(Rectangle rectangle, double angle) {
        return getFrontLeftX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getFrontLeftX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = getAntiClockwiseRotation(rotation, angle);
        return getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getFrontLeftZ(Rectangle rectangle, double angle) {
        return getFrontLeftZ(rectangle.getPoint().getRotation(), rectangle.getPoint().getZ(), rectangle.getDistanceToVertex(), angle);
    }

    private static double getFrontLeftZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = getAntiClockwiseRotation(rotation, angle);
        return getNewZ(coord, newRotation, distanceToVertex);
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
        newRotation = getClockwiseRotation(newRotation, angle);
        return getNewX(coord, newRotation, distanceToVertex);
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
        newRotation = getClockwiseRotation(newRotation, angle);
        return getNewZ(coord, newRotation, distanceToVertex);
    }

    public static double getBackLeftX(Rectangle rectangle, double angle) {
        return getBackLeftX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackLeftX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = getReversedRotation(rotation);
        newRotation = getClockwiseRotation(newRotation, angle);
        return getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getBackLeftZ(Rectangle rectangle, double angle) {
        return getBackLeftZ(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getZ(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackLeftZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = getReversedRotation(rotation);
        newRotation = getClockwiseRotation(newRotation, angle);
        return getNewZ(coord, newRotation, distanceToVertex);
    }

    public static double getBackRightX(Rectangle rectangle, double angle) {
        return getBackRightX(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getX(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackRightX(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = getReversedRotation(rotation);
        newRotation = getAntiClockwiseRotation(newRotation, angle);
        return getNewX(coord, newRotation, distanceToVertex);
    }

    public static double getBackRightZ(Rectangle rectangle, double angle) {
        return getBackRightZ(
                rectangle.getPoint().getRotation(),
                rectangle.getPoint().getZ(),
                rectangle.getDistanceToVertex(),
                angle);
    }

    private static double getBackRightZ(double rotation, double coord, double distanceToVertex, double angle) {
        double newRotation = getReversedRotation(rotation);
        newRotation = getAntiClockwiseRotation(newRotation, angle);
        return getNewZ(coord, newRotation, distanceToVertex);
    }

    public static double getNewX(double x, double amountToChange) {
        return x + amountToChange;
    }

    private static double getNewX(double x, double rotation, double speed) {
        double amountChangeX = getAmountToChangedX(rotation, speed);
        return getNewX(x, amountChangeX);
    }

    private static double getNewZ(double z, double amountToChange) {
        return z + amountToChange;
    }

    private static double getNewZ(double z, double rotation, double speed) {
        double amountChangeZ = getAmountToChangedZ(rotation, speed);
        return getNewZ(z, amountChangeZ);
    }

    public static double getAmountToChangedX(double rotation, double speed) {
        double coefficientX = Math.sin(rotation);
        return coefficientX * speed;
    }

    public static double getAmountToChangedZ(double rotation, double speed) {
        double coefficientZ = Math.cos(rotation);
        return coefficientZ * speed;
    }

    private static double getAntiClockwiseRotation(double orientation, double angle) {
        double newRotation = orientation - angle;
        if(newRotation < 0) {
            //TODO May not need -1, just don't do MAX_ROTATE - rotate
            newRotation = (MAX_ROTATE - newRotation) * -1;
        }
        return newRotation;
    }

    private static double getClockwiseRotation(double orientation, double angle) {
        if(orientation  >= MAX_ROTATE - angle) {
            orientation = 0;
        } else {
            orientation = orientation + angle;
        }
        return orientation;
    }

    private static double getReversedRotation(double orientation) {
        double newRotation = orientation - Math.PI;
        if (newRotation < 0) {
            return MAX_ROTATE + newRotation;
        }
        return newRotation;
    }

    public static boolean isIntersectRectangle(Rectangle rectangle, double startX, double startZ, double endX, double endZ) {
        double angle = MathUtils.getAngleRadians(rectangle.getWidth() /2, rectangle.getDistanceToVertex());
        boolean doesIntersect;
        //Front
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                MathUtils.getFrontLeftX(rectangle, angle), MathUtils.getFrontLeftZ(rectangle, angle),
                MathUtils.getFrontRightX(rectangle, angle), MathUtils.getFrontRightZ(rectangle, angle));


        if(doesIntersect) {
            return true;
        }

        //Left
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                MathUtils.getFrontLeftX(rectangle, angle), MathUtils.getFrontLeftZ(rectangle, angle),
                MathUtils.getBackLeftX(rectangle, angle), MathUtils.getBackLeftZ(rectangle, angle));

        if(doesIntersect) {
            return true;
        }

        //Right
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                MathUtils.getFrontRightX(rectangle, angle), MathUtils.getFrontRightZ(rectangle, angle),
                MathUtils.getBackRightX(rectangle, angle), MathUtils.getBackRightZ(rectangle, angle));

        if(doesIntersect) {
            return true;
        }

        //Back
        doesIntersect = MathUtils.isLinesIntersect(
                startX, startZ,
                endX, endZ,
                MathUtils.getBackLeftX(rectangle, angle), MathUtils.getBackLeftZ(rectangle, angle),
                MathUtils.getBackRightX(rectangle, angle), MathUtils.getBackRightZ(rectangle, angle));

        return doesIntersect;
    }


}
