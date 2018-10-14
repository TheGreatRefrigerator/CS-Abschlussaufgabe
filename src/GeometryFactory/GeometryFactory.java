package GeometryFactory;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WktInvalidException;

import static FeaturePackage.Buffer.standardizeAngle;

public class GeometryFactory {
    /*--Methods--*/

    /**
     * Creates a Point Geometry from a wktString and checks it for validity
     * @param wktPoint - the Point WKT String
     * @return {Point} - the created Point
     * @throws WktInvalidException - in case of wrong WKT format
     */
    public static Point createPoint(String wktPoint) throws WktInvalidException {
        String[] wkt = wktPoint.split("[()]");


        // variable = (if this) ? (assign this) : (else assign this);
        // this is called a shorthand if -> https://stackoverflow.com/questions/4461996/short-if-else-statement
        String type = wkt[0].split(" ").length == 2 ? wkt[0].split(" ")[1] : "";
        /*
         * Create a type variable for the point type String type. If you split "POINT " at " ",
         * length will be 1, if you split "POINT Z" length will be 2 -> ["POINT","Z"]
         * if (wkt[0].split(" ").length == 2) type = wkt[0].split(" ")[1];
         * else type = "";
         */


        // split coords at ' ' to get single coords
        String[] coords = wkt[1].split(" ");

        // check wkt validity
        Helper.checkWktValidity(type, coords.length);

        // If its a point with measure value, use Constructor with m
        // get measure value as last element of coords array because wkt is: POINT {Z}M (X Y {Z} ->M<-)
        if (type.contains("M")) {
            double m = Double.parseDouble(coords[coords.length - 1]);
            double[] newCoords = new double[coords.length - 1];
            for (int i = 0; i < coords.length - 1; i++) {
                newCoords[i] = Double.parseDouble(coords[i]);
            }
            return new Point(m, newCoords);
        }

        // create new double array to save the coords that are still in string format
        double[] coord = new double[coords.length];

        // loop through and convert string coords to doubles
        for (int i = 0; i < coord.length; i++) {
            coord[i] = Double.parseDouble(coords[i]);
        }

        // make a new point from the now double coords and return it.
        return new Point(coord);
    }

    /**
     * Creates a new Point with exactly the same constructor as in Point.java for
     * arbitrary dimensions
     * @param coords - the n dimensional coordinates
     * @return {Point} - the created Point
     */
    public static Point createPoint(double... coords) {
        return new Point(coords);
    }

    // we will not need this as the above constructor does exactly the same
    // public static Point createPoint(double[] coords) {
    // return new Point(coords);
    // }

    /**
     * Creates a new Point with measure value and arbitrary dimensions
     * @param m      - the measure value
     * @param coords - the n dimensional coordinates
     * @return {Point} - the created Point
     */
    public static Point createPointM(double m, double... coords) {
        return new Point(m, coords);
    }

    /**
     * Create a new Point derived from another Point and the direction as angle from north (0)
     * @param p        - base point
     * @param nAngle   - normalized angle (0 - 360) from north
     * @param distance - distance to base point
     * @return {Point} - the created Point
     */
    public static Point createPoint(Point p, double nAngle, double distance) {
        double x = p.getX() + distance * Math.cos(Math.toRadians(nAngle));
        double y = p.getY() + distance * Math.sin(Math.toRadians(nAngle));
        return new Point(new double[]{x, y});
    }

    /**
     * Creates a Line Geometry from a wktString and checks it for validity
     * @param wktLine - the Line WKT String
     * @return {Line} - the created Point
     * @throws WktInvalidException - in case of wrong WKT format
     */
    public static Line createLine(String wktLine) throws WktInvalidException {
        // split wkt line on '(' and ')' so "LINESTRING (12 31, 2 4, 2 1, 12 0)" will be "LINESTRING ", "12 31, 2 4, 2 1, 12 0"
        String[] lineWktParts = wktLine.split("[()]");

        // access first entry of wkt extract the wkt type
        String type = lineWktParts[0].split(" ").length == 2 ? lineWktParts[0].split(" ")[1] : "";

        // split second entry of wkt to get array of points "21 31", "2 4", "2 1", "12 0"
        String[] points = lineWktParts[1].split(",");

        // create Point Array from String Array 'points'
        Point[] pointArray = Helper.generatePointArray(type, points);

        // return the from the pointArray created Line
        try {
            return new Line(pointArray);
        } catch (DimensionalException e) {
            throw new WktInvalidException();
        }
    }

    /**
     * Create Line from Coordinates Array
     * @param coords - Array of Coordinate Arrays ([[x1,y1,..],[x2,y2,..],..)
     * @return {Line} - the created Line
     */
    public static Line createLine(double[]... coords) throws DimensionalException {
        Point[] pointArray = new Point[coords.length];
        for (int i = 0; i < coords.length; i++) {
            pointArray[i] = createPoint(coords[i]);
        }
        return new Line(pointArray);
    }

    /**
     * Create Line from Points
     * @param points - Array of Points for the Line
     * @return {Line} - created Line
     */
    public static Line createLine(Point... points) throws DimensionalException {
        return new Line(points);
    }

    /**
     * Create Polygon from Point array
     * First and Last Points need to be the same
     * @param points - Points array
     * @return {Polygon} - the created Polygon
     */
    public static Polygon createPolygon(Point... points) throws DimensionalException, InvalidPolygonException {
        return new Polygon(points);
    }

    /**
     * Create Polygon from String wkt
     * @param wktPolygon - String wkt
     * @return - created Polygon
     */
    public static Polygon createPolygon(String wktPolygon) throws WktInvalidException {
        // split polygon wkt on '(' and ')' so "POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10))"
        // will be "POLYGON ", "35 10, 45 45, 15 40, 10 20, 35 10"
        String[] wkt = wktPolygon.split("[()]");

        // Add check for Polygons with holes as we do not support them
        // POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10), (20 30, 35 35, 30 20, 20 30))
        if (wkt.length > 3) {
            System.out.println("Currently we do not support polygons with inner rings.");
            throw new WktInvalidException();
        }
        // access first entry of wkt to extract the wkt type
        String type = wkt[0].split(" ").length == 2 ? wkt[0].split(" ")[1] : "";

        // split third entry of wkt to get array of points "35 10", "45 45", "15 40", "10 20", "35 10"
        String[] points = wkt[2].split(",");

        // create Point Array from String Array 'points'
        Point[] pointArray = Helper.generatePointArray(type, points);
        try {
            return new Polygon(pointArray);
        } catch (DimensionalException | InvalidPolygonException e) {
            throw new WktInvalidException();
        }
    }

    /**
     * Create Polygon from coordinates
     * @param coords - Array of Coordinates for the Polygon
     * @return - created Polygon
     */
    public static Polygon createPolygon(double[]... coords) throws InvalidPolygonException, DimensionalException {
        Point[] pointArray = new Point[coords.length];
        for (int i = 0; i < coords.length; i++) {
            pointArray[i] = createPoint(coords[i]);
        }
        return new Polygon(pointArray);
    }
}
