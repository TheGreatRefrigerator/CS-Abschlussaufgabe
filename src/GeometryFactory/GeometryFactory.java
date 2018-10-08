package GeometryFactory;

import Exceptions.DimensionalException;
import Exceptions.WktInvalidException;

public class GeometryFactory {
	/**
	 * Creates a Point Geometry from a wktString and checks it for validity
	 * @param wktPoint - the Point WKT String
	 * @return {Point} - the created Point
	 * @throws WktInvalidException - in case of wrong WKT format
	 */
	public static Point createPoint(String wktPoint) throws WktInvalidException {
		String[] wkt = wktPoint.split("[()]");

		// ok this might seem a little complicated :) this is the short writing of the
		// below
		// in general you can say
		// variable = (if this) ? (assign this) : (else assign this);
		// this is called a shorthand if ->
		// https://stackoverflow.com/questions/4461996/short-if-else-statement
		String type = wkt[0].split(" ").length == 2 ? wkt[0].split(" ")[1] : "";

		/*
		 * //Create a type variable for the point type String type; // if you split
		 * "POINT " at " ", length will be 1, if you split "POINT Z" length will be 2 ->
		 * ["POINT","Z"] if (wkt[0].split(" ").length == 2) type = wkt[0].split(" ")[1];
		 * else type = "";
		 */

		// split coords at ' ' to get single coords
		String[] coords = wkt[1].split(" ");

		// check wkt validity
		Helper.checkWktValidity(type, coords.length);
		// If its a point with measure value, use Constructor with m
		// get measure value aka last element of coords array as wkt is POINT {Z}M (X Y {Z} ->M<-)
		if (type.contains("M")){
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
	 * 
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
	 * @param m - the measure value
	 * @param coords - the n dimensional coordinates
	 * @return {Point} - the created Point
	 */
	public static Point createPointM(double m, double... coords) {
		return new Point(m, coords);
	}


	/**
	 * Creates a Line Geometry from a wktString and checks it for validity
	 * @param wktLine - the Line WKT String
	 * @return {Line} - the created Point
	 * @throws WktInvalidException - in case of wrong WKT format
	 */
	public static Line createLine(String wktLine) throws WktInvalidException {
		// split wkt line on '(' and ')' so "LINESTRING (12 31, 2 4, 2 1, 12 0)" will be "LINESTRING ", "12 31, 2 4, 2 1, 12 0"
		String[] wkt = wktLine.split("[()]");

		// access first entry of wkt extract the wkt type
		String type = wkt[0].split(" ").length == 2 ? wkt[0].split(" ")[1] : "";

		// split second entry of wkt to get array of points "21 31", "2 4", "2 1", "12 0"
		String[] points = wkt[1].split(",");
		
		// creates a array of Points with as many points as there are in the wkt string
		Point[] pointArray = new Point[points.length];
		
		for (int i = 0; i < points.length; i++) {

			String[] coords = points[i].split(" ");

			Helper.checkWktValidity(type, coords.length);

			// if it is a point with measure value, the last value contains the  measure value
			// So if the type has M in the name, we parse without the last value
			boolean isMeasurePoint = type.contains("M");
			int lengthM = isMeasurePoint ? coords.length - 1 : coords.length;
			// create array to save the double values of the coords
			double[] pointValues = new double[lengthM];
			// if we want to support arbitrary dimensions
			for (int x = 0; x < lengthM; x++) {
				// convert to double
				String stringValue = coords[x];
				pointValues[x] = Double.parseDouble(stringValue);
			}

			double lrsValue = 0;
			if (isMeasurePoint) {
				lrsValue = Double.parseDouble(coords[lengthM]);
			}
			// create new point with double coord array and fill position of
			// the Point array with this point
			// M Points have to be generated differently as the M value is in a
			// separate attribute
			pointArray[i] = isMeasurePoint ? new Point(lrsValue ,pointValues) : new Point(pointValues);
			

		}
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
	public static Polygon createPolygon(Point... points) { return new Polygon(points); }

	/**
	 * Create Polygon from String wkt 
	 * @param wkt - String wkt 
	 * @return - created Polygon
	 */
//	public static Polygon createPolygon(String wkt) {
//		//TODO create point array from WKT
//		return new Polygon(wkt);
//	}

	/**
	 * Create Polygon from coordinates
	 * @param coords - Array of Coordinates for the Polygon
	 * @return - created Polygon
	 */
//	public static Polygon createPolygon(double... coords) {
//		//
//		return new Polygon();
//	}
}
