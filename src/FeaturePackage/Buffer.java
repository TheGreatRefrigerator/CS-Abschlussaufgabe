package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

/**
 * This class stores all buffer methods for the different features.
 * As there is heavy calculation involved we place it in a new class
 * so the Feature classes will stay clean.
 */
class Buffer {

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
        double x = point.getX();
        double y = point.getY();
        // initialize pointArray and buffer
        Point[] pointArray = new Point[size];
        Polygon buffer = null;
        for (int i = 0; i < size; i++) {
            // start with angle 0 and increase by angle each time
            double alpha = i * angle;
            // calculate new coordinate values
            double newX = x + range * Math.cos(Math.PI * alpha / 180);
            // is same as double newX = x + range * Math.cos(Math.toRadians(alpha));
            double newY = y + range * Math.sin(Math.PI * alpha / 180);
            pointArray[i] = new Point(new double[]{newX, newY});
        }
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
        Polygon buffer = null;
        return buffer;
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
        return buffer;
    }
}
