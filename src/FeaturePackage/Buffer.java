package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class stores all buffer methods for the different features.
 * As there is heavy calculation involved we place it in a new class
 * so the Feature classes will stay clean.
 */
public class Buffer {

    /**
     * Generates a Buffer around a point geometry
     *
     * @param point      - the point geometry
     * @param range      - the buffer range in point coordinate units
     * @param smoothness - this defines how many points will be added in an 90 degree angle
     *                   for 0 the result is a diamond
     * @return {Polygon} - the buffer geometry
     */
    static Polygon pointBuffer(Point point, double range, int smoothness) {
        // smoothness 0 should create points in 90 degree angle (90/1) -> +1
        double angle = 90 / ((double) smoothness + 1);
        // number of points to be created; also for array length specification
        int size = 4 * (1 + smoothness) + 1;
        // initialize pointArray and buffer
        Point[] pointArray = new Point[size];
        Polygon buffer = null;
        for (int i = 0; i < size - 1; i++) {
            // start with angle 0 and increase by angle each time
            double alpha = i * angle;
            // calculate new coordinate values
            pointArray[i] = GeometryFactory.createPoint(point, alpha, range);
        }
        pointArray[size - 1] = pointArray[0];
        try {
            buffer = new Polygon(pointArray);
        } catch (DimensionalException | InvalidPolygonException ignored) {
            // this should never happen
        }
        return buffer;
    }

    /**
     * Generates a Buffer around a line geometry
     *
     * @param line       - the line geometry
     * @param range      - the buffer range in line coordinate units
     * @param smoothness - this defines how many points will be added in an 90 degree angle
     * @return {Line} - the buffer geometry
     */
    static Polygon lineBuffer(Line line, double range, int smoothness) {
        // store Line points in Point Array
        Point[] points = line.getPoints();
        // create ArrayList for buffer Points
        // ArrayList instead of Array because it has dynamic size
        // -> process all LineSegments
        ArrayList<Point> bufferPointList = processLineSegments(points, range, smoothness);
        // process Line from other side (reverse Line Points
        bufferPointList.addAll(processLineSegments(reverse(points), range, smoothness));
        bufferPointList.add(bufferPointList.get(0));
        // parse ArrayList back to array
        Point[] bufferPoints = new Point[bufferPointList.size()];
        bufferPointList.toArray(bufferPoints);
        // trim coordinates to readable value depending on input range
        trimCoords(bufferPoints, range, 4);
        // initiate Polygon from buffer Points
        Polygon buffer = null;
        try {
            buffer = new Polygon(bufferPoints);
        } catch (DimensionalException | InvalidPolygonException ignored) {
            // this should never happen
        }
        return buffer;
    }

    /**
     * Generates a Buffer around a line geometry
     *
     * @param polygon    - the polygon geometry
     * @param range      - the buffer range in polygon coordinate units
     * @param smoothness - this defines how many points will be added in an 90 degree angle
     * @return {Polygon} - the buffer geometry
     */
    static Polygon polygonBuffer(Polygon polygon, double range, int smoothness, boolean inside) {
        // store Polygon points in Point Array in clockwise direction
        Point[] points = clockwiseGeomPoints(polygon);;

        // reverse points if we want an inside buffer
        if (inside) points = reverse(points);

        // create ArrayList for buffer Points
        // ArrayList instead of Array because it has dynamic size
        // -> process all LineSegments
        ArrayList<Point> bufferPointList = processLineSegments(points, range, smoothness, true);
        bufferPointList.add(bufferPointList.get(0));
        // parse ArrayList back to array
        Point[] bufferPoints = new Point[bufferPointList.size()];
        bufferPointList.toArray(bufferPoints);
        // trim coordinates to readable value depending on input range
        trimCoords(bufferPoints, range, 4);
        // initiate Polygon from buffer Points
        Polygon buffer = null;
        try {
            buffer = new Polygon(bufferPoints);
        } catch (DimensionalException | InvalidPolygonException ignored) {
            // this should never happen
        }
        return buffer;
    }

    /**
     * Checks a Polygon geometry and outputs its points in clockwise direction
     * @param poly - Polygon to check
     * @return {Point[]} - Polygon points array
     */
    private static Point[] clockwiseGeomPoints(Polygon poly) {
        Point[] points = poly.getPoints();
        // calculate sum of angles to determine the direction
        double sum = 0;
        double sum2 = 0;
        for (int p = 0; p < points.length - 3; p++) {
            sum += getCurveAngle(points, p);
            sum2 += getCurveAngle(reverse(points), p);
        }
        return sum > sum2 ? points : reverse(points);
    }

    private static ArrayList<Point> processLineSegments(Point[] points, double range, int smoothness) {
        return processLineSegments(points, range, smoothness, false);
    }

    private static ArrayList<Point> processLineSegments(Point[] points, double range, int smoothness, boolean polygon) {
        // create Point List to return later
        ArrayList<Point> bufferPointList = new ArrayList<>();
        // if this is a Polygon geometry, we need to add the second Point to the end of the Array
        // to be able to draw the last angle
        if (polygon) {
            ArrayList<Point> tempPoints = new ArrayList<Point>(Arrays.asList(points));
            tempPoints.add(points[1]);
            points = tempPoints.toArray(points);
        }
        // boolean to decide if we need to add a first point to the processed segment
        // not needed if we had a curve Angle below 180 degrees
        boolean addFirstPoint = true;
        // information if the buffer was adjusted in the last segment
        // -> removing of previous buffer points because of very sharp angles
        boolean adjustBuffer = false;
        for (int i = 0; i < points.length - 1; i++) {
            // calculate the angle between this and the next line segment
            Point current = points[i];
            Point next = points[i + 1];
            double nCurveAngle = getCurveAngle(points, i);

            // calculate the angle of the current segment 0 0 to 1 1 would be 45 degrees
            double nSegment = normalizeAngle(current.angle(next));
            // angle from the current point to the buffer point we want to add
            double nInit = (nSegment + 270) % 360;

            // create Point in Buffer range from first Point if last curve angle < 180
            // add it to the list if we need it
            if (addFirstPoint) {
                Point firstPoint = GeometryFactory.createPoint(current, nInit, range);
                bufferPointList.add(firstPoint);
            }
            //
            if (!(polygon && i == points.length - 2)) {
                if (nCurveAngle > 180) {

                    if (adjustBuffer) {
                        double nNewInit = normalizeAngle(next.angle(bufferPointList.get(bufferPointList.size() - 1)));
                        Point newFirstPoint = GeometryFactory.createPoint(next, nNewInit, range);
                        bufferPointList.add(newFirstPoint);
                        double nNewCurveAngle = nCurveAngle - getCurveAngle(nNewInit, nInit);
                        bufferPointList.addAll(createCurvePoints(next, nNewInit, nNewCurveAngle, range, smoothness));
                        adjustBuffer = false;
                    } else {
                        bufferPointList.addAll(createCurvePoints(next, nInit, nCurveAngle, range, smoothness));
                    }

                    addFirstPoint = true;
                } else {
                    // create buffer intersection point
                    Point supportPoint = GeometryFactory.createPoint(next, nInit, range);
                    double angleFromSupport = (nSegment + 180) % 360;
                    double newRange;
                    if (nCurveAngle == 90) {
                        newRange = range;
                    } else {
                        double oppositeAngle = nCurveAngle / 2;
                        newRange = Math.tan(Math.PI * standardizeAngle(oppositeAngle) / 180) * range;
                    }
                    Point intersectionPoint = GeometryFactory.createPoint(supportPoint, angleFromSupport, newRange);
                    // add it to the list if it does not conflict with current buffer
                    // calculate angle of line point -> new intersection point and line point -> old buffer point
                    //
                    adjustBuffer = false;
                    while (getCurveAngle(bufferPointList.get(bufferPointList.size() - 1), next, intersectionPoint) > 180) {
                        bufferPointList.remove(bufferPointList.size() - 1);
                        adjustBuffer = true;
                    }

                    if (!adjustBuffer) bufferPointList.add(intersectionPoint);
                    addFirstPoint = false;

                    if (polygon && i == points.length - 3) {
                        bufferPointList.remove(0);
                    }
                }
            }
        }
        return bufferPointList;
    }

    private static ArrayList<Point> createCurvePoints(Point p, double initAngle, double nCurveAngle, double range, int smoothness) {
        double nProcessAngle = nCurveAngle - 180;
        ArrayList<Point> curvePoints = new ArrayList<>();
        // number of points to be created in curve
        // calculate the angle after which a new point has to be added in curves
        double nAngle = 90 / ((double) smoothness + 1);
        int size = (int) Math.ceil(nProcessAngle / nAngle);
        for (int i = 0; i < size; i++) {
            double currentAngle = (initAngle + (nAngle * i)) % 360;
            // calculate new Point
            curvePoints.add(GeometryFactory.createPoint(p, currentAngle, range));
        }
        double nextAngle = initAngle + nProcessAngle;
        curvePoints.add(GeometryFactory.createPoint(p, nextAngle, range));
        return curvePoints;
    }

    /**
     * Orient angle respective to north and show only positive values, range 0-360
     * North = 0, East = 90, South = 180, West = 270
     *
     * @param standardAngle - an angle in standard format
     * @return {double} - normalized angle
     */
    private static double normalizeAngle(double standardAngle) {
        return ((standardAngle * (-1) + 450) % 360);
    }

    /**
     * Switch normalized angle back to standard format
     * North = 90, East = 0, South = - 90, West = 180
     *
     * @param normalAngle - an angle in normalized format
     * @return {double} - standard angle
     */
    public static double standardizeAngle(double normalAngle) {
        normalAngle = (normalAngle * (-1) + 450) % 360;
        return normalAngle > 180 ? normalAngle - 360 : normalAngle;
    }

    /**
     * Reverses to point list to process the reverse Line for second buffer half
     *
     * @param points - Line point array
     * @return {Point[]} - reversed Line Point Array
     */
    static Point[] reverse(Point[] points) {
        Point[] pointsReverse = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsReverse[points.length - (i + 1)] = points[i];
        }
        return pointsReverse;
    }

    /**
     * @param points
     * @param index
     * @return
     */
    private static double getCurveAngle(Point[] points, int index) {
        double nCurveAngle;
        if (index < points.length - 2) {
            Point current = points[index];
            Point next = points[index + 1];
            Point next2 = points[index + 2];
            nCurveAngle = getCurveAngle(current, next, next2);
        } else {
            nCurveAngle = 360;
        }
        return nCurveAngle;
    }

    private static double getCurveAngle(Point one, Point two, Point three) {
        double nBearing1 = normalizeAngle(two.angle(three));
        double nBearing2 = normalizeAngle(two.angle(one));
        return getCurveAngle(nBearing1, nBearing2);
    }

    private static double getCurveAngle(double nBearing1, double nBearing2) {
        return (nBearing1 - nBearing2 + 360) % 360;
    }

    private static void trimCoords(Point[] list, double range, int accuracy) {
        String sRange = Double.toString(range);
        String[] doubleParts = sRange.split("\\.");
        int places;
        if (doubleParts.length == 2) {
            places = doubleParts[1].length() + accuracy;
            for (Point p : list) {
                p.setX(new BigDecimal(p.getX()).setScale(places, RoundingMode.HALF_DOWN).doubleValue());
                p.setY(new BigDecimal(p.getY()).setScale(places, RoundingMode.HALF_DOWN).doubleValue());
            }
        }
    }
}
