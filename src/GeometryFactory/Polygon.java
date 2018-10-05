package GeometryFactory;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Geometry {
	// Attributes of the Polygon Geometry
	private Point[] points;
	private int d;
	private String wktType;

	/**
	 * Creates Polygon from List of Points
	 * @param points - list of points
	 */
//	[Punkt1, Punkt2, Punkt3]

	public Polygon(Point... points) {
		if (points.length > 3) {
			Point first = points[0];
			Point last = points[points.length - 1];
			// If the first and last point is equal -> accept points array
			if (first.is(last)) {
				d = 0;
				boolean M = true;
				// check if all points have the same dimension
				for (Point p : points) {
					try {
						p.getLrsValue();
					} catch (NullPointerException e) {
						M = false;
					}

					if (d == 0) { //TODO
						d = p.getDimension();
					} else {
						if (p.getDimension() != d) {
							// TODO throw Exception

						}
					}
				}
				this.points = points;
			} else {
//				TODO No closed Polygon
			}
		} else {
//			TODO throw error
		}
	}

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
