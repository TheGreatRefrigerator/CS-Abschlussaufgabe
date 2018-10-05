package GeometryFactory;

import Exceptions.WKTRepresentationException;

import java.util.Arrays;

public class Point implements Geometry {
    // Attributes of the Point Geometry
    private double[] coordinates;
    private int d; // dimension
    private Double mValue; // see https://en.wikipedia.org/wiki/Well-known_text and
    // https://en.wikipedia.org/wiki/Linear_referencing
    private String wktType;

    /**
     * Create Point from coordinates with double values
     *
     * @param coords - the coordinates array
     */
    public Point(double... coords) {
        mValue = null;
        d = 0;
        coordinates = coords;
        for (double i : coords) {
            d++;
        }
        wktType = Helper.getWktType(d);

    }

    /**
     * @param lrsValue
     * @param coords
     */
    public Point(double lrsValue, double... coords) {
        this.mValue = lrsValue;
        coordinates = coords;
        d = 0;
        for (double i : coords) {
            d++;
        }
        wktType = Helper.getWktType(d, mValue);
    }
    // Constructor II with a double array, which contains the coordinates
//    public Point(double[] coords) { // coords = Array-Name
//        coordinates = coords;
//        d = 0;
//        for (double i : coords) {
//            d++;
//        }
//    }
    // TODO: copyconstructor for point with coordinates array
    // Constructor for deep-copy of point  (-> point b)
    // Parameter p: Point that shall be copied
//	public Point(Point p) {
//		x = p.x;
//		y = p.y;
//	}
//
//	public Point() {
//		x = 0;
//		y = 0;
//	}
    // // Getter/Setter

    /**
     * Returns the point coordinates
     *
     * @return {double[]} the coordinates array
     */
    public double[] getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value for a specific coordinate
     *
     * @param position - the dimensional position (x = 1, y = 2 ...)
     * @param value    - the new value
     */
    public void setCoordinates(int position, double value) {
        coordinates[position - 1] = value;
    }

    /**
     * @return {double} - x coordinate of point
     */
    public double getX() {
        return coordinates[0];
    }

    /**
     * @return {double} - y coordinate of point
     */
    public double getY() {
        return coordinates[1];
    }

    /**
     * Set x coordinate of point
     *
     * @param x - the x value
     */
    public void setX(double x) {
        coordinates[0] = x;
    }

    /**
     * Set y coordinate of point
     *
     * @param y - the y value
     */
    public void setY(double y) {
        coordinates[1] = y;
    }

    /**
     * Returns the measure value of the point
     *
     * @return the point value
     */
    public double getLrsValue() {
        double d = 0;

        try {
            d = mValue;
        } catch (NullPointerException e) {
            System.out.println("No Value");
        }
        return d;
    }

    /**
     * Returns the point dimension
     *
     * @return {int} the point dimension
     */
    public int getDimension() {
        return d;
    }

    public String getWktType() {
        return wktType;
    }


    // METHODS

    // Override from Geometry
    @Override
    public String getWKT() throws WKTRepresentationException {
        /*
         * get WKT only if it's a valid dimension (2D, 2D+ M, 3D, 3D + M)
         * https://en.wikipedia.org/wiki/Well-known_text 'Coordinates for geometries may
         * be 2D (x, y), 3D (x, y, z), 4D (x, y, z, m) with an m value that is part of a
         * linear referencing system or 2D with an m value (x, y, m).'
         */
        System.out.println(mValue == null);
        if (!wktType.isEmpty()) {
            StringBuilder wkt = new StringBuilder("POINT ");
            if (!"".equals(wktType)) {
                wkt.append(wktType).append(" ");
            }
            wkt.append('(');
            for (int j = 0; j < coordinates.length; j++) {
                double i = coordinates[j];
                wkt.append(String.valueOf(i));
                if (j < coordinates.length - 1) {
                    wkt.append(" ");
                }
            }
            // append measure value if it exists
            if (!mValue.isNaN()) {
                wkt.append(' ').append(mValue.toString());
            }

            wkt.append(")");

            return wkt.toString();
        } else {
            throw new WKTRepresentationException();
        }
    }

    /**
     * Checks if the Points are the same by comparing all the attributes.
     * Returns true only if all the attributes are the same
     * @param p - Point to compare
     * @return {boolean} - true for same Point
     */
    public boolean is(Point p) {
        return ((Arrays.equals(coordinates, p.getCoordinates())) && (d == p.getDimension()) && (mValue == p.getLrsValue()) && (wktType.equals(p.getWktType())));
    }
//    private double[] coordinates;
//    private int d; // dimension
//    private Double mValue; // see https://en.wikipedia.org/wiki/Well-known_text and
//    // https://en.wikipedia.org/wiki/Linear_referencing
//    private String wktType;
//	// Distance from this point to another
//	// Parameter b: other Point
//	// return: distance
//	public double distanceTo(Point b) {
//		double a_quadrat = Math.pow(b.getX() - getX(), 2); // wuuuhu!
//		double b_quadrat = Math.pow(b.getY() - getY(), 2);
//		double dist = Math.sqrt(a_quadrat + b_quadrat);
//		return dist;
//	}
//
//	// Angle between this point and another
//	// Parameter b: other point
//	// return: angle in degree
//	public double getAngle(Point p) {
//		double angle = Math.toDegrees(Math.atan2(p.y - this.getY(), p.x - this.getX()));
//		if (angle < 0)
//			angle += 360;
//		return angle;
//	}

    // Area`
//	public double Area() {
//		return 0;
//	}

    // Expansion
//	public double Extent() {
//		return 0;
//	}

    // Centroid
    // public double Centroid() {
    // ? return this; }

//	// Method for comparing objects -> here Point with reference point b
//	public boolean equals(Object o) {
//		Point b = (Point) o;
//		return ((this.getX() == b.getX())) && ((this.getY() == b.getX()));
//
//	}

}
