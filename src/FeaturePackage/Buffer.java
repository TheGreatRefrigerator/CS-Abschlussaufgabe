package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class stores all buffer methods for the different features.
 * As there is heavy calculation involved a new class is created for it
 * so the Feature classes will stay clean.
 */
public class Buffer {
    // to avoid passing these values down the whole function chain they are declared as private attributes
    // as they are used all over the place
    private static Point[] points;
    private static double range;
    private static int smoothness;
    private static boolean addInitPoint = true;
    private static boolean bufferWasTrimmed = false;

    /**
     * Generates a Buffer around a point geometry
     * @param point    - the point geometry
     * @param distance - the buffer range in point coordinate units
     * @param accuracy - defines how many points will be added in an 90 degree angle
     * @return {Polygon} - the buffer geometry
     */
    static Polygon pointBuffer(Point point, double distance, int accuracy) {
        // set attributes
        range = distance;
        smoothness = accuracy;
        // smoothness 0 should create points in 90 degree angle (90/1) -> +1
        // example: Point with smoothness = 0
        smoothness += 1;
        double angle = 90 / ((double) smoothness);
        // number of points to be created; also for array length specification
        // example: size will be 5 there are 4 points for the polygon buffer
        //          and the last has to be the first.
        int size = 4 * (smoothness) + 1;
        // initialize pointArray and buffer
        Point[] bufferPoints = new Point[size];
        Polygon buffer = null;
        // runs size - 1 times as last point needs to be created from first
        for (int i = 0; i < size - 1; i++) {
            // start with angle 0 and increase by angle each time
            // example: 0, 90, 180, 270
            double alpha = i * angle;
            // create new point from input point, angle and buffer distance
            bufferPoints[i] = GeometryFactory.createPoint(point, alpha, range);
        }
        // create the last point from the first point
        bufferPoints[size - 1] = bufferPoints[0];
        // trim coordinates to readable value depending on input range
        trimCoords(bufferPoints, 4);
        // create the polygon geometry from the point array
        try {
            buffer = new Polygon(bufferPoints);
        } catch (DimensionalException | InvalidPolygonException ignored) {
            // this should never happen
        }
        return buffer;
    }

    /**
     * Generates a Buffer around a line geometry
     * @param line     - the line geometry
     * @param distance - the buffer range in line coordinate units
     * @param accuracy - this defines how many points will be added in an 90 degree angle
     * @return {Line} - the buffer geometry
     */
    static Polygon lineBuffer(Line line, double distance, int accuracy) {
        // set attributes
        range = distance;
        smoothness = accuracy;
        // store Line points in Point Array
        points = line.getPoints();
        // use ArrayList instead of Array for buffer Points because it has dynamic size
        // create the first half of the buffer Polygon by processing all LineSegments
        // from one direction
        ArrayList<Point> bufferPointList = processLineSegments();
        // create second half of the buffer Polygon by processing LineSegments
        // from the other direction by reversing the points array
        points = reverse(points);
        bufferPointList.addAll(processLineSegments());
        // create the last point from the first point to close polygon
        bufferPointList.add(bufferPointList.get(0));
        // parse ArrayList back to array
        Point[] bufferPoints = new Point[bufferPointList.size()];
        bufferPointList.toArray(bufferPoints);
        // trim coordinates to readable value depending on input range
        trimCoords(bufferPoints, 4);
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
     * @param polygon    - the polygon geometry
     * @param distance      - the buffer range in polygon coordinate units
     * @param accuracy - this defines how many points will be added in an 90 degree angle
     * @return {Polygon} - the buffer geometry
     */
    static Polygon polygonBuffer(Polygon polygon, double distance, int accuracy, boolean inside) {
        // set attributes
        range = distance;
        smoothness = accuracy;
        // store Polygon points in Point Array in clockwise direction
        points = clockwiseGeomPoints(polygon);

        // reverse points if the buffer should be on the inside of the polygon
        if (inside) points = reverse(points);

        // use ArrayList instead of Array because it has dynamic size
        // process all LineSegments with polygon: true
        ArrayList<Point> bufferPointList = processLineSegments(true);
        // create the last point from the first to close polygon
        bufferPointList.add(bufferPointList.get(0));
        // parse ArrayList back to array
        Point[] bufferPoints = new Point[bufferPointList.size()];
        bufferPointList.toArray(bufferPoints);
        // trim coordinates to readable value depending on input range
        trimCoords(bufferPoints, 4);
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
     * Orient standard angle respective to north and show only positive values, range 0-360
     * North = 0, East = 90, South = 180, West = 270
     * @param standardAngle - an angle in standard format
     * @return {double} - normalized angle
     */
    public static double normalizeAngle(double standardAngle) {
        return ((standardAngle * (-1) + 450) % 360);
    }

    /**
     * Switch normalized angle to standard format
     * North = 90, East = 0, South = - 90, West = 180
     * @param normalAngle - an angle in normalized format
     * @return {double} - standard angle
     */
    public static double standardizeAngle(double normalAngle) {
        normalAngle = (normalAngle * (-1) + 450) % 360;
        return normalAngle > 180 ? normalAngle - 360 : normalAngle;
    }

    /**
     * Checks a Polygon geometry and outputs its points in clockwise direction
     * @param poly - Polygon to check
     * @return {Point[]} - Polygon points array
     */
    private static Point[] clockwiseGeomPoints(Polygon poly) {
        points = poly.getPoints();
        // calculate sum of angles to determine the direction
        double sum = 0;
        double sum2 = 0;
        for (int p = 0; p < points.length - 3; p++) {
            sum += getCurveAngle(points, p);
            sum2 += getCurveAngle(reverse(points), p);
        }
        return sum > sum2 ? points : reverse(points);
    }

    private static ArrayList<Point> processLineSegments() {
        return processLineSegments(false);
    }

    /**
     * Process the
     * @param polygon - if this is called for a polygon
     * @return {ArrayList<Point>} - the array list of all buffer points
     */
    private static ArrayList<Point> processLineSegments(boolean polygon) {
        // if this is a Polygon geometry, the second Point is added to the end of the array
        // to be able to draw the last angle
        if (polygon) {
            ArrayList<Point> tempPoints = new ArrayList<Point>(Arrays.asList(points));
            tempPoints.add(points[1]);
            points = tempPoints.toArray(points);
        }
        // boolean to decide whether the first point needs to be added to the buffer point array
        // not needed if the curve Angle is below 180 degrees
        addInitPoint = true;
        // information if the buffer was adjusted in the last segment
        // -> removing of previous buffer points because of very sharp angles
        bufferWasTrimmed = false;
        // create Point List to return later
        ArrayList<Point> bufferPointList = new ArrayList<>();
        // process each line segment
        for (int i = 0; i < points.length - 1; i++) {
            processLineSegment(i, bufferPointList, polygon);

        }
        return bufferPointList;
    }

    /**
     * Processes one geometry line segment for buffer point creation
     * @param index - current index of the geometry points array
     * @param bufferPointList - the buffer point array to append to
     * @param polygon - if this is called for a polygon
     */
    private static void processLineSegment(int index, ArrayList<Point> bufferPointList,boolean polygon) {
        // get the current segments start and endpoint
        Point current = points[index];
        Point next = points[index + 1];
        // calculate the angle of the current segment 0 0 to 1 1 would be 45 degrees
        double nSegment = normalizeAngle(current.angle(next));
        // angle from the current point to the buffer point to add
        double nBufferInit = (nSegment + 270) % 360;

        // create Point in Buffer range from first Point if last curve angle < 180
        // add it to the list if needed
        // if segment goes from 0 0 to 5 0, the created point will be at 0 r
        // where r is the specified buffer distance
        if (addInitPoint) {
            Point firstPoint = GeometryFactory.createPoint(current, nBufferInit, range);
            bufferPointList.add(firstPoint);
        }
        // as the second point was added to the points array again for polygons the angle
        // between the start and second point does not have to be processed again
        if (!(polygon && index == points.length - 2)) {
            processAngle(nSegment, index, bufferPointList, polygon);
        }
    }

    /**
     * Processes the Angle between two geometry line segments
     * @param nSegment - angle in degrees from north of the first segment
     * @param index - current index of the geometry points array
     * @param bufferPointList - the buffer point array to append to
     * @param polygon - if this is called for a polygon
     */
    private static void processAngle(double nSegment, int index, ArrayList<Point> bufferPointList, boolean polygon) {
        // calculate the angle between this and the next line segment
        // 0 0 to 1 0 to 1 1 would be 90 degrees
        // 0 0 to 1 0 to 1 -1 would be 270 degrees
        double nCurveAngle = getCurveAngle(points, index);
        Point next = points[index + 1];
        // angle from the current point to the buffer point to add
        double nBufferInit = (nSegment + 270) % 360;
        // when the line makes a right turn (>180)
        if (nCurveAngle > 180) {
            rightTurn(next, nBufferInit, nCurveAngle, bufferPointList);
        }
        // when the line makes a left turn (<180)
        else {
            leftTurn(nSegment, nCurveAngle, bufferPointList, index, polygon);
        }
    }

    /**
     * Processes left turns in line and polygon geometries
     * @param nSegment - the angle in degrees from north of the segment before the turn
     * @param nCurveAngle - the angle of the turn in degree
     * @param bufferPointList - the buffer point array to append to
     * @param index - the current index in the points array
     * @param polygon - if this is called for a polygon
     */
    private static void leftTurn(double nSegment, double nCurveAngle, ArrayList<Point> bufferPointList, int index, boolean polygon) {
        // angle from the current point to the buffer point to add
        double nBufferInit = (nSegment + 270) % 360;
        Point turnPoint = points[index + 1];
        // create support Point for calculations like the initPoint
        // if segment goes from 0 0 to 5 0, the support point will be at 5 r
        // where r is the specified buffer distance
        Point supportPoint = GeometryFactory.createPoint(turnPoint, nBufferInit, range);
        double angleFromSupport = (nSegment + 180) % 360;
        // Calculate the distance supportPoint -> intersectionPoint
        // using the range (endpoint -> supportPoint) and the angle at the intersectionPoint
        // which will always be half of the curve angle
        // The triangle:
        // - endpoint of the current line segment
        // - support point
        // - intersection point to be created
        double oppositeAngle = nCurveAngle / 2;
        double newRange = Math.tan(Math.toRadians(standardizeAngle(oppositeAngle))) * range;
        Point intersectionPoint = GeometryFactory.createPoint(supportPoint, angleFromSupport, newRange);

        bufferWasTrimmed = false;
        // calculate angle of line point -> new intersection point and line point -> old buffer point
        // if the angle is greater than 180 it would intersect with the current buffer points
        // so points have to be removed from the buffer point list until there will be no intersection
        while (getCurveAngle(bufferPointList.get(bufferPointList.size() - 1), turnPoint, intersectionPoint) > 180) {
            bufferPointList.remove(bufferPointList.size() - 1);
            bufferWasTrimmed = true;
        }
        // add intersectionPoint to the list if it does not conflict with current buffer
        if (!bufferWasTrimmed) bufferPointList.add(intersectionPoint);
        // if the buffer was trimmed it is not needed to create the first point for the next line segment
        // as it would lay inside the buffer
        addInitPoint = false;

        // for polygons first point from the list has to be removed for proper buffer creation
        if (polygon && index == points.length - 3) {
            bufferPointList.remove(0);
        }
    }

    /**
     * Processes right turns in line and polygon geometries
     * @param next - the turn point
     * @param nBufferInit - angle from the turn point to the first buffer point of the curve
     * @param nCurveAngle - the angle of the turn in degrees
     * @param bufferPointList - the buffer point array to append to
     */
    private static void rightTurn(Point next, double nBufferInit, double nCurveAngle, ArrayList<Point> bufferPointList) {
        // if the buffer was trimmed, the curve has to start from the last buffer point
        if (bufferWasTrimmed) {
            // get the angle to the last buffer point
            double nNewInit = normalizeAngle(next.angle(bufferPointList.get(bufferPointList.size() - 1)));
            // create the first point of the curve with direction to the last buffer point
            Point newFirstPoint = GeometryFactory.createPoint(next, nNewInit, range);
            bufferPointList.add(newFirstPoint);
            // adjust the curve angle by the difference of the normal to the new first curve point
            double nNewCurveAngle = nCurveAngle - getCurveAngle(nNewInit, nBufferInit);
            createCurvePoints(next, nNewInit, nNewCurveAngle, bufferPointList);
            // reset the trimmed variable
            bufferWasTrimmed = false;
        } else {
            // otherwise normal curve creation
            createCurvePoints(next, nBufferInit, nCurveAngle, bufferPointList);
        }
        // after a right turn, the buffer point for the start point of the next
        // line segment can be created without conflicts
        addInitPoint = true;
    }

    /**
     * Creates curve points and appends them to the buffer point list
     * @param p - the point of the geometry the curve is created from
     * @param initAngle - the angle from north from p to the first curve point
     * @param nCurveAngle - the angle of the turn in degrees
     * @param bufferPointList - the buffer point array to append to
     */
    private static void createCurvePoints(Point p, double initAngle, double nCurveAngle, ArrayList<Point> bufferPointList) {
        // create the curve angle from the turn angle
        double nProcessAngle = nCurveAngle - 180;
        // calculate the angle after which a new point has to be added in curves
        double nAngle = 90 / ((double) smoothness + 1);
        // number of points to be created in curve
        // calculated from process angle and smoothness angle
        int size = (int) Math.ceil(nProcessAngle / nAngle);
        for (int i = 0; i < size; i++) {
            // set angle for current point
            double currentAngle = (initAngle + (nAngle * i)) % 360;
            // calculate new Point and add to buffer points
            bufferPointList.add(GeometryFactory.createPoint(p, currentAngle, range));
        }
        // add the last point
        double nextAngle = initAngle + nProcessAngle;
        bufferPointList.add(GeometryFactory.createPoint(p, nextAngle, range));
    }

    /**
     * Reverses to point list to process the reverse Line for second buffer half
     * @param points - Line point array
     * @return {Point[]} - reversed Line Point Array
     */
    private static Point[] reverse(Point[] points) {
        Point[] pointsReverse = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsReverse[points.length - (i + 1)] = points[i];
        }
        return pointsReverse;
    }

    /**
     * Returns the clockwise turn angle between the current and next geometry line segment
     * @param points - the geometry points array
     * @param index - the current index
     * @return {double} - the turn angle in degrees
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

    /**
     * Returns the clockwise turn angle between three points
     * @param one - first point
     * @param two - second point
     * @param three - third point
     * @return {double} - the turn angle in degrees
     */
    private static double getCurveAngle(Point one, Point two, Point three) {
        double nBearing1 = normalizeAngle(two.angle(three));
        double nBearing2 = normalizeAngle(two.angle(one));
        return getCurveAngle(nBearing1, nBearing2);
    }

    /**
     * Returns the clockwise turn angle between two bearings (normalized angles from north)
     * @param nBearing1 - first bearing
     * @param nBearing2 - second bearing
     * @return {double} - the turn angle in degrees
     */
    private static double getCurveAngle(double nBearing1, double nBearing2) {
        return (nBearing1 - nBearing2 + 360) % 360;
    }

    /**
     * Trims all values of the result buffer point array
     * depending on the places of range input value of the buffer method.
     * The accuracy will leave more places from the calculated position.
     * eg. range = 0.5 , value = 8.2362341234234
     * accuracy = 0 -> value = 8.2
     * accuracy = 4 -> value = 8.23623
     * this is important for latitude/longitude values
     * @param list - the full buffer point array to process
     * @param accuracy - places to leave after range precision
     */
    private static void trimCoords(Point[] list, int accuracy) {
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
