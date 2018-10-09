package GeometryFactory;

import Exceptions.DimensionalException;
import Exceptions.WKTRepresentationException;

public class Line implements Geometry {
    // Attributes of the Line Object
    private Point[] points;
    private int d;
    private String wktType;

    /**
     * Constructor will take any amount of Points because of the ...(spread)
     * operator
     * @param points - The array of Points
     */
    public Line(Point... points) throws DimensionalException {
        this("init", points);
    }

    /**
     * Constructor will take any amount of Points because of the ...(spread)
     * operator. Optionally the known wkt type can be passed.
     * @param wktType - The WKT Type of the Line
     * @param points  - The array of Points
     */
    public Line(String wktType, Point... points) throws DimensionalException {
        d = 0;
        boolean wktCheck = wktType.equals("init"); // set wkt Check to true if no wktType was passed
        // check if all points have the same dimension
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            // if it is the first time running we are at the first point
            // get its wkt type and compare the next points wkt type with this
            if (wktType != null && wktCheck) {
                if (wktType.equals("init")) {
                    wktType = p.getWktType();

                } else if (!wktType.equals(p.getWktType())) {
                    wktType = null;
                }
            }
            // Check points for same dimension
            // get dimension of the first point and compare others this value
            if (d == 0) {
                d = p.getDimension();
            } else {
                // if same value keep the dimension value
                if (p.getDimension() != d) {
                    // else no compatible dimensions of input
                    throw new DimensionalException(i);
                }
            }
        }
        // set the points Attribute
        this.points = points;
        this.wktType = wktType;
    }

    public String getWktType() {
        return wktType;
    }

    public void setWktType(String wktType) {
        this.wktType = wktType;
    }

    /**
     * Checks if the current Geometry is Wkt Conform
     * @return
     */
    @Override
    public boolean isWktConform() {
        return (wktType != null);
    }

    /**
     * Returns the Dimension of the Line
     * @return {int} - the dimension
     */
    public int getDimension() {
        return d;
    }

    public Point[] getPoints() {
        return points;
    }

    // METHODS

    /**
     * Returns the starting point of a line, NO double coords!
     * The point itself has a method to show the coordinates
     * @return the starting point
     */
    public Point getStart() {
        return points[0];
    }

    /**
     * Returns the Endpoint of a Line
     * @return the Endpoint
     */
    public Point getEnd() {
        return points[points.length - 1];
    }

    /**
     * Returns a Point at a specific position in the Line
     * @param position - the point number (start = 1, next Point = 2 ...)
     * @return {Point} - Point at requested Position
     */
    public Point getPoint(int position) {
        return points[position - 1];
    }

    // Returns WKT representation of the Linestring
    @Override
    public String getWKT() throws WKTRepresentationException {
        return Helper.buildWkt("linestring", points, wktType);
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

    /**
     * Move a Point to a new position
     * @param number  - the point number to change
     * @param {Point} point - the new point
     */
    public void setPoint(int number, Point point) {
        points[number - 1] = point;
    }

    // TODO do we need this for Lines ?
//	// Area of a geometry
//	public double Area() {
//		return 0;
//	}

//	// Expansion
//	public double Extent() {
//		return Double.parseDouble(null); /* start.distanceTo(end); */
//	};

}
