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

    // Returns true if the lines intersect, otherwise false.
    public boolean isLinesIntersect(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                    double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {
        double line1LengthX, line1LengthY, line2LengthX, line2LengthY;

        line1LengthX = line1EndX - line1StartX;
        line1LengthY = line1EndZ - line1StartZ;
        line2LengthX = line2EndX - line2StartX;
        line2LengthY = line2EndZ - line2StartZ;

        double sDenominator = (-line2LengthX * line1LengthY + line1LengthX * line2LengthY);

        if(sDenominator == 0) {
            if(isLinesParallel(line1StartX,line1StartZ,line1EndX,line1EndZ,line2StartX,line2StartZ,line2EndX,line2EndZ)) {
                //check if collinear
                System.out.println("Lines are parallel");
            } else {
                return false;
            }
        }

        double s, t;
        double tDenominator = (-line2LengthX * line1LengthY + line1LengthX * line2LengthY);
        double sNumerator = (-line1LengthY * (line1StartX - line2StartX) + line1LengthX * (line1StartZ - line2StartZ));
        double tNumerator = ( line2LengthX * (line1StartZ - line2StartZ) - line2LengthY * (line1StartX - line2StartX));

        if(sDenominator == 0) {
            if(isLinesParallel(line1StartX,line1StartZ,line1EndX,line1EndZ,line2StartX,line2StartZ,line2EndX,line2EndZ)) {
                //check if collinear
                System.out.println("Lines are parallel");
            } else {
                return false;
            }
        }

        s = sNumerator / sDenominator;
        t = tNumerator / tDenominator;

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // Collision detected
            return true;
        }

        return false; // No collision
    }

    private boolean isCollinearAndTouching(double line1StartX, double line1StartZ, double line1EndX, double line1EndZ,
                                           double line2StartX, double line2StartZ, double line2EndX, double line2EndZ) {

        double slopeLine1 = 0, slopeLine2 = 0;

        //(delta y)/(delta x)
        //delta x
        double slopeDenominator = (line1EndX - line1StartX);
        if (slopeDenominator == 0) {
            //Line is vertical! No change in x
            //TODO Check if the lines touch, otherwise return false
        } else {
            slopeLine1 = (line1EndZ - line1StartZ) / slopeDenominator;
            slopeLine1 = (Math.round(slopeLine1 * 10000));
            slopeLine1 = slopeLine1 / 10000;
        }

        //delta x
        slopeDenominator = (line2EndX - line2StartX);
        if (slopeDenominator == 0) {
            //Line is vertical! No change in x
            //TODO Check if the lines touch, otherwise return false
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

//        TODO Don't need to check this separately, below method will cover it.
//        if (slopeLine1 == 0) {
//            //line is horizontal
//        }

        if (line1[0][0] == line2[0][0] && line1[0][1] == line2[0][1]) {
            //Lines share a start coordinate
            return true;
        } else if(line1[0][0] < line2[0][0]) {
            //Line1 starts further left
            if(line1[1][0] == line2[0][0]) {
                //line2 starts at the end of line 1
                return true;
            } else if(line2[0][0] >= line1[0][0] && line2[0][0] <= line1[1][0]) {
                //Line 2 start fits between the start and end of line 1
                return true;
            } else {
                //Lines are not collinear, should never have gotten here. Check the logic before here.
                return false;
            }
        } else if(line1[0][0] > line2[0][0]) {
            //Line2 starts further left
            if(line2[1][0] == line1[0][0]) {
                //Line 1 starts at the end of line 2
                return true;
            } else if(line1[0][0] >= line2[0][0] && line1[0][0] <= line2[1][0]) {
                //Line 1 fits between the start and end of line 1
                return true;
            } else {
                //Lines are not collinear, should never have gotten here. Check the logic before here.
                return false;
            }
        } else {
            //Lines are not collinear, should never have gotten here. Check the logic before here.
            return false;
        }
    }

}
