package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class stores all buffer methods for the different features.
 * As there is heavy calculation involved we place it in a new class
 * so the Feature classes will stay clean.
 */
public class Buffer {

    /**
     * Generates a Buffer around a point geometry
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
        // initiate Polygon from buffer Points
        Polygon buffer = null;
        try {
            buffer = new Polygon(bufferPoints);
        } catch (DimensionalException | InvalidPolygonException ignored) {
            // this should never happen
        }
        return buffer;
    }

    private static ArrayList<Point> processLineSegments(Point[] points, double range, int smoothness) {
        // create Point List to return later
        ArrayList<Point> bufferPointList = new ArrayList<>();
        // boolean to decide if we need to add a first point to the processed segment
        // not needed if we had a curve Angle below 180 degrees
        boolean addFirstPoint = true;
        for (int i = 0; i < points.length - 1; i++) {
            // calculate the angle between this and the next line segment
            Point current = points[i];
            Point next = points[i + 1];
            double nCurveAngle = getCurveAngle(points, i);

            // calculate the angle of the current segment 0 0 to 1 1 would be 45 degrees
            double nSegment = normalizeAngle(current.angle(next));
            // angle from the current point to the buffer point we want to add
            double nInit = (nSegment + 270) % 360;

            if (addFirstPoint) {
                // create Point in Buffer range from first Point if last curve angle < 180
                Point firstPoint = GeometryFactory.createPoint(current, nInit, range);
                // add it to the list
                bufferPointList.add(firstPoint);
            }
            //
            if (nCurveAngle > 180) {
                bufferPointList.addAll(createCurvePoints(next, nInit, nCurveAngle, range, smoothness));
                addFirstPoint = false;
            } else {
                // create buffer intersection point
                Point supportPoint = GeometryFactory.createPoint(next, nInit, range);
                double angleFromSupport = (nSegment + 180) % 360;
                double newRange;
                if (nCurveAngle == 90) {
                    newRange = range;
                } else {
                    double oppositeAngle = (getCurveAngle(nInit, normalizeAngle(next.angle(points[i+2]))));
                    newRange = Math.tan(Math.PI * standardizeAngle(oppositeAngle) / 180) * range;
                }
                Point intersectionPoint = GeometryFactory.createPoint(supportPoint, angleFromSupport, newRange);
                // add it to the list
                bufferPointList.add(intersectionPoint);
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
     * @param standardAngle - an angle in standard format
     * @return {double} - normalized angle
     */
    private static double normalizeAngle(double standardAngle) {
        return ((standardAngle * (-1) + 450) % 360);
    }

    /**
     * Switch normalized angle back to standard format
     * North = 90, East = 0, South = - 90, West = 180
     * @param normalAngle - an angle in normalized format
     * @return {double} - standard angle
     */
    public static double standardizeAngle(double normalAngle) {
        normalAngle = (normalAngle * (-1) + 450) % 360;
        return normalAngle > 180 ? normalAngle - 360 : normalAngle;
    }

    /**
     * Generates a Buffer around a line geometry
     * @param polygon    - the polygon geometry
     * @param range      - the buffer range in polygon coordinate units
     * @param smoothness - this defines how many points will be added in an 90 degree angle
     * @return {Polygon} - the buffer geometry
     */
    static Polygon polygonBuffer(Polygon polygon, double range, int smoothness) {
        Polygon buffer = null;
        // check which side to start on
        return buffer;
    }

    /**
     * Reverses to point list to process the reverse Line for second buffer half
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
     *
     * @param points
     * @param index
     * @return
     */
    private static double getCurveAngle(Point[] points, int index) {
        double nCurveAngle;
        if (index < points.length - 2) {
            Point current = points[index];
            Point next = points[index+1];
            Point next2 = points[index+2];
            nCurveAngle = normalizeAngle(next.angle(next2)) - normalizeAngle(next.angle(current));
            if (nCurveAngle < 0) nCurveAngle += 360;
        } else {
            nCurveAngle = 360;
        }
        return nCurveAngle;
    }

    private static double getCurveAngle(double nAngle1, double nAngle2) {
        double nCurveAngle = (nAngle2 - nAngle1 + 360) % 360;
        return nCurveAngle;
    }
}
