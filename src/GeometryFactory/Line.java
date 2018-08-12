package GeometryFactory;

public class Line implements Geometry {

	// Attributes of the Line Object
	private Point[] points;

	/**
	 * Constructor will take any amount of Points because of the ...(spread) operator
	 * @param points - The array of Points
	 */
	public Line(Point... points) { // Line, weil der Konstruktor wie die KLasse heißen muss
		//TODO check if all points have the same dimension
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

	public Line(String wkt) {
	}

	public Line() {

	}

	// METHODEN

	/**
	 * Returns the starting point of a line
	 * @return the starting point
	 */
	public Point getStart() {
		return points[0];
	} // returnt einen Punkt, keine doubles(also keine Koordinaten)
	// der Punkt selbst hat dafür die Fähigkeit die Koordinaten anzuzeigen

	/**
	 * Returns the Endpoint of a Line
	 * @return the Endpoint
	 */
	public Point getEnd() {
		return points[points.length - 1];
	}

	// Returns WKT Repräsentation des line string
	public String getWKT() {
		// TODO like in Point.java check the dimension before returning wkt and return exception if not valid
		// TODO loop through coords and build WKT
		return null;
	}

	/**
	 * Set a new starting point for the line
	 * @param start - the new starting point
	 */
	public void setStart(Point start) {
		points[0] = start;
	}

	/**
	 * Set a new Endpoint for the line
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
		return Double.parseDouble(null); /*start.distanceTo(end);*/
	};

	@Override
	public String getInfo() {
		// TODO this will be moved to the Feature Interface as the geometry should not have additional information itself
		return null;
	}

	@Override
	public Polygon buffer(double range) {
		// TODO this will also be moved to the Feature Interface
		return null;
	}

}
