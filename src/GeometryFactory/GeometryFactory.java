package GeometryFactory;

import Exceptions.WKTDimensionalException;

public class GeometryFactory {

	public static Point createPoint(String wktString) throws WKTDimensionalException {
		String[] wkt = wktString.split("[()]");

		// ok this might seem a little complicated :) this is the short writing of the
		// below
		// in general you can say
		// variable = (if this) ? (assign this) : (else assign this);
		// this is called a shorthand if ->
		// https://stackoverflow.com/questions/4461996/short-if-else-statement
		String type = wkt[0].split(" ").length == 2 ? wkt[0].split(" ")[1] : "Normal";

		/*
		 * //Create a type variable for the point type String type; // if you split
		 * "POINT " at " ", length will be 1, if you split "POINT Z" length will be 2 ->
		 * ["POINT","Z"] if (wkt[0].split(" ").length == 2) type = wkt[0].split(" ")[1];
		 * else type = "Normal";
		 */
		// just for checking
		System.out.println(type);

		// split coords at ' ' to get single values
		String[] values = wkt[1].split(" ");

		// check if wkt is valid
		switch (type) {
			case "Normal":
				if (values.length != 2) throw new WKTDimensionalException();
				break;
			case "M":
				if (values.length != 3) throw new WKTDimensionalException();
				break;
			case "Z":
				if (values.length != 3) throw new WKTDimensionalException();
				break;
			case "ZM":
				if (values.length != 4) throw new WKTDimensionalException();
				break;
		}

		// TODO check if type is POINT, POINT Z, POINT M or POINT ZM and else return
		if (type.equals("M") || type.equals("ZM")){
			// get measure value aka last element of
			double m = Double.parseDouble(values[values.length - 1]);
			double[] newCoords = new double[values.length - 1];
			for (int i = 0; i < values.length - 1; i++) {
				newCoords[i] = Double.parseDouble(values[i]);
			}
			return new Point(m, newCoords);
		}

		for (String cord : values) {
			System.out.print(cord + ", ");
		}
		System.out.println();
		System.out.println();

		// create new double array to save the values that are still in string format
		double[] coord = new double[values.length];
		
		
		

		// loop through and convert string values to doubles
		for (int i = 0; i < coord.length; i++) {
			coord[i] = Double.parseDouble(values[i]);
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

	/**
	 * Creates a new Point with measure value and arbitrary dimensions
	 * @param m - the measure value
	 * @param coords - the n dimensional coordinates
	 * @return {Point} - the created Point
	 */
	public static Point createPointM(double m, double... coords) {
		return new Point(m, coords);
	}

	// we will not need this as the above constructor does exactly the same
	// public static Point createPoint(double[] coords) {
	// return new Point(coords);
	// }

	// Line-WKT splitten...
	public static Line createLine(String wktLine) throws WKTDimensionalException {
		String[] wkt = wktLine.split("[()]");
//		"LINESTRING (12 31, 2 4, 2 1, 12)" -> "LINESTRING ", "12 31, 2 4, 2 1, 12"
		String[] points = wkt[1].split(",");
		
		// creates a array of Points with as many points as there are in the wkt string
		Point[] pointArray = new Point[points.length];
		
		for (int i = 0; i < points.length; i++) {
			
			String[] coords = points[i].split(" ");
			if (coords.length > 3) {
				throw new WKTDimensionalException();
			}
			// create array to save the double values of the coords
			double[] pointValues = new double[coords.length];

			// wenn wir dimensionstechnisch flexibel sein wollen
			for (int x = 0; x < coords.length; x++) {
				// ...und in Double umwandeln
//				System.out.println(value[x]);
				String svalue = coords[x];
				pointValues[x] = Double.parseDouble(svalue);
			}
			
			// create new point with double coord array and append to the Point array
			pointArray[i] = new Point(pointValues);
			

		}
		// return the newly from the pointArray created Line
		return new Line(pointArray);
	}

	/**
	 * Create Line from Coordinates Array
	 * @param coords - Array of Coordinate Arrays ([[x1,y1,..],[x2,y2,..],..)
	 * @return {Line} - the created Line
	 */
	public static Line createLine(double[]... coords) {
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
	public static Line createLine(Point... points) {
		return new Line(points);
	}



	public static Polygon createPolygon(String wkt) {
		return new Polygon(wkt);
	}

	public static Polygon createPolygon(double... coords) {
		return new Polygon();
	}
}
