package GeometryFactory;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Geometry {
	// Attributes of the Polygon Geometry
	private Point[] points;
	private int d;
	private String wktType;

//	/**
//	 * Creates Polygon from List of Points
//	 * @param points - list of points
//	 */
//	public Polygon(Point... points) {
//		this.points = points;
//	}
//

	/**
	 * Creates Polygon from Point Array
	 * @param points - Point Array
	 */
//	public Polygon(Point[] points) {
//		for (Point p : points) {
//			this.points.add(p);
//		}
//	}

	// Methods

	// Getter
	public Point[] getPoints() {
		return this.points;
	}

	// WKT
	@Override
	public String getWKT() {
		String wkt = "Polygon( ";
		for (Point p : this.points) {
//			wkt += String.valueOf(p.getX()) + " " + String.valueOf(p.getY()) + ",";
		}
		wkt += ")";
		return wkt;
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
