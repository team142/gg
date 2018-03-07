package com.team142.gg.server.utils;

import com.team142.gg.server.model.mappable.meta.PlaceableElement;
import com.team142.gg.server.model.mappable.meta.SpaceTimePoint;

import java.util.Arrays;
import java.util.Comparator;

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

    public static double getGradientOfLine(double x1, double z1, double x2, double z2) {
        return ((z2-z1)/(x2-x1));
    }

    public static double getYIntersectOfLine(double x1, double z1, double gradient) {
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
        if((0 < t1 && t1 < 1) || (0 < t2 && t2 < 1)) return true;
        return false;
    }

    private boolean isLinesParallel(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                    double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {
        double slopeLine1, slopeLine2;
        slopeLine1 = (line1EndZ - line1StartZ) / (line1EndX - line1StartX);
        slopeLine2 = (line2EndZ - line2StartZ) / (line2EndX - line2StartX);
        return slopeLine1 == slopeLine2;
    }

    private boolean isCollinearLinesIntersect(double start, double end, double pointChecking) {
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
    private boolean isVerticalLinesIntersect(double line1StartZ, double line1EndZ, double line2StartZ, double line2EndZ) {
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
    private boolean isCollinearAndTouching(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                           double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {

        double slopeLine1 = 0, slopeLine2 = 0;

        //(delta y)/(delta x)
        //delta x
        double slopeDenominator = (line1EndX - line1StartX);
        if (slopeDenominator == 0) {
            //Line is vertical! No change in x

            if(line1EndX == line2StartX) {
                return isVerticalLinesIntersect(line1StartZ, line1EndZ, line2StartZ, line2EndZ);
            } else {
                return false;
            }
        } else {
            slopeLine1 = (line1EndZ - line1StartZ) / slopeDenominator;
            slopeLine1 = (Math.round(slopeLine1 * 10000));
            slopeLine1 = slopeLine1 / 10000;
        }

        //delta x
        slopeDenominator = (line2EndX - line2StartX);
        if (slopeDenominator == 0) {
            //Line is vertical! No change in x
            if(line1EndX == line2StartX) {
                return isVerticalLinesIntersect(line1StartZ, line1EndZ, line2StartZ, line2EndZ);
            } else {
                return false;
            }
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
        } else if(line1[0][0] < line2[0][0]) {
            return  isCollinearLinesIntersect(line1[0][0], line1[1][0], line2[0][0]);
        } else if(line1[0][0] > line2[0][0]) {
            return  isCollinearLinesIntersect(line2[0][0], line2[1][0], line1[0][0]);
        } else {
            //Lines are not collinear, should never have gotten here. Check the logic before here.
            return false;
        }
    }

    public boolean isLinesIntersect(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                    double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {

        double line1LengthX = line1EndX - line1StartX;
        double line1LengthY = line1EndZ - line1StartZ;
        double line2LengthX = line2EndX - line2StartX;
        double line2LengthY = line2EndZ - line2StartZ;

        double sDenominator = (-line2LengthX * line1LengthY + line1LengthX * line2LengthY);
        if(sDenominator == 0) {
            //check if parallel
            if(isLinesParallel(line1StartX,line1StartZ,line1EndX,line1EndZ,line2StartX,line2StartZ,line2EndX,line2EndZ)) {
                //check if collinear
                return isCollinearAndTouching(line1StartX,line1StartZ,line1EndX,line1EndZ,line2StartX,line2StartZ,line2EndX,line2EndZ);
            } else {
                return false;
            }
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

}
