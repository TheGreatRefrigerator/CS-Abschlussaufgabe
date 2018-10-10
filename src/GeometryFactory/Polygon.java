package GeometryFactory;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WKTRepresentationException;

public class Polygon implements Geometry {
    // Attributes of the Polygon Geometry
    private Point[] points;
    private int d;
    private String wktType;

    /**
     * Creates Polygon from List of Points
     * @param points - list of points
     */
    public Polygon(Point... points) throws DimensionalException, InvalidPolygonException {
        this("init", points);
    }

    /**
     * Creates Polygon from List of Points
     * @param points - list of points
     */
    public Polygon(String wktType, Point... points) throws DimensionalException, InvalidPolygonException {
        if (points.length > 3) {
            Point first = points[0];
            Point last = points[points.length - 1];
            // If the first and last point is equal -> accept points array
            if (first.is(last)) {
                d = 0;
                boolean wktCheck = wktType.equals("init"); // set wkt Check to true if no wktType was passed
                // check if all points have the same dimension
                // last point is the first so we don't need to check it
                for (int i = 0; i < points.length - 1; i++) {
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
                this.points = points;
                this.wktType = wktType;
            } else {
                throw new InvalidPolygonException();
            }
        } else {
            throw new InvalidPolygonException();
        }
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

    /**
     * Returns a Point at a specific position in the Line
     * @param position - the point number (start = 1, next Point = 2 ...)
     * @return {Point} - Point at requested Position
     */
    public Point getPoint(int position) {
        return points[position - 1];
    }

    /**
     * Move a Point to a new position
     * @param number  - the point number to change
     * @param {Point} point - the new point
     */
    public void setPoint(int number, Point point) {
        points[number - 1] = point;
    }

    // WKT
    @Override
    public String getWKT() throws WKTRepresentationException {
        return Helper.buildWkt("polygon", points, wktType);
    }

    /**
     * Checks if the current Geometry is Wkt Conform
     * @return
     */
    @Override
    public boolean isWktConform() {
        return (wktType != null);
    }
//
//	// Centroid
//	public Point Centroid() {
//		double x_centroid = 0.0, y_centroid = 0.0;
//		for (Point p : points) {
////			x_centroid += p.getX();
////			y_centroid += p.getY();
//		}
//		return new Point(new double[] { x_centroid, y_centroid });
//	}
}
