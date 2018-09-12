package GeometryFactory;

import Exceptions.WKTDimensionalException;

public class Line implements Geometry {

	// Attributes of the Line Object
	private Point[] points;
	private int d;
	private String wktType;

	/**
	 * Constructor will take any amount of Points because of the ...(spread)
	 * operator
	 * 
	 * @param points - The array of Points
	 */
	public Line(Point... points) { // Line, weil der Konstruktor wie die KLasse heißen muss
		int dimension;
		// check if all points have the same dimension
		for (Point p : points) {
			if (d) { //TODO
				dimension = p.getDimension();
			} else {
				if (p.getDimension() != d) {
					// TODO throw Exception
				}
			}
		}
		d = dimension;
		// set the points Attribute
		this.points = points;
	}

	// Konstruktor mit Koordinaten // wenn wir neue Punkte mit Koordinaten
	// definieren wollen
	// Start: (x1/y1)
	// End: (x2/y2)

	public Line(int dimension, double... coords) {

//		this.start = new Point(x1, y1);
//		this.end = new Point(x2, y2);
	}

	public String getWktType() {
		return wktType;
	}

	public void setWktType(String wktType) {
		this.wktType = wktType;
	}

	public Point[] getPoints() {
		return points;
	}

	public Line() {

	}

	// METHODEN

	/**
	 * Returns the starting point of a line
	 * 
	 * @return the starting point
	 */
	public Point getStart() {
		return points[0];
	} // returnt einen Punkt, keine doubles(also keine Koordinaten)
		// der Punkt selbst hat dafür die Fähigkeit die Koordinaten anzuzeigen

	/**
	 * Returns the Endpoint of a Line
	 * 
	 * @return the Endpoint
	 */
	public Point getEnd() {
		return points[points.length - 1];
	}

	// Returns WKT Repräsentation des line string
	@Override
	public String getWKT() {
		if (d > 1 && d < 4) {
			String wkt = "LINESTRING ";
			if (d == 3) {
				wkt += 'Z';
			}
			
			if (get. != null) {
				
			}
		} else {
			// throw exception
		}
		// TODO like in Point.java check the dimension before returning wkt and return
		// exception if not valid
		// TODO loop through coords and build WKT
		return null;
	}

	/**
	 * Set a new starting point for the line
	 * 
	 * @param start - the new starting point
	 */
	public void setStart(Point start) {
		points[0] = start;
	}

	/**
	 * Set a new Endpoint for the line
	 * 
	 * @param end - the new endpoint
	 */
	public void setEnd(Point end) {
		points[points.length - 1] = end;
	}

	// TODO do we need this for Lines ?
//	// Fläche einer Geometrie
//	public double Area() {
//		return 0;
//	}

	// Ausdehnung
	public double Extent() {
		return Double.parseDouble(null); /* start.distanceTo(end); */
	};

}
