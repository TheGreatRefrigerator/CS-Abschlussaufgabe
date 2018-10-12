package GeometryFactory;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import FeaturePackage.Buffer;

import java.util.Arrays;

public class Point implements Geometry {
    /*--Attributes--*/
    // Attributes of the Point Geometry
    private double[] coordinates;
    private int d; // dimension
    private Double m;
    private String wktType;

    /*--Constructors--*/
    /**
     * Create Point from coordinates with double values
     *
     * @param coords - the coordinates array
     */
    public Point(double... coords) {
        m = null;
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
    // see https://en.wikipedia.org/wiki/Well-known_text and
    // https://en.wikipedia.org/wiki/Linear_referencing
    public Point(double lrsValue, double... coords) {
        this.m = lrsValue;
        coordinates = coords;
        d = 0;
        for (double i : coords) {
            d++;
        }
        wktType = Helper.getWktType(d, m);
    }

    // TODO: copyconstructor for point with coordinates array
    // Constructor for deep-copy of point  (-> point b)
    // Parameter p: Point that shall be copied
    //	public Point(Point p) {
    //		x = p.x;
    //		y = p.y;
    //	}

    /*--Getter & Setter--*/

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

    public double getX() {
        return coordinates[0];
    }

    public void setX(double x) {
        coordinates[0] = x;
    }

    public double getY() {
        return coordinates[1];
    }

    public void setY(double y) {
        coordinates[1] = y;
    }

    public int getDimension() {
        return d;
    }

    public String getWktType() {
        return wktType;
    }

    /**
     * Returns the measure value of the point
     *
     * @return the point value
     */
    public double getLrsValue() {
        double d = 0;
        try {
            d = m;
        } catch (NullPointerException e) {
            System.out.println("Check if the point has a Value with Point.hasLrsValue() before getting it.");
        }
        return d;
    }

    /**
     * Sets the measure Value of the point
     *
     * @param lrsValue
     */
    public void setLrsValue(Double lrsValue) {
        this.m = lrsValue;
        try {
            Helper.checkWktValidity(wktType, d);
            if (!wktType.contains("M")) {
                wktType += 'M';
            }
        } catch (WktInvalidException ignored) {
            // if this is no valid WKT point it doesn't matter
            // it can still have a value
        }
    }

    /*--Methods--*/

    /**
     * Return true if the point has a M value and false if it is not defined
     */
    public boolean hasLrsValue() {
        return !(m == null);
    }


    /**
     * Checks if the current Geometry is Wkt Conform
     */
    @Override
    public boolean isWktConform() {
        return (wktType != null);
    }

    @Override
    public String getWKT() throws WKTRepresentationException {
        if (!(wktType == null)) {
            return Helper.buildWkt("point", new Point[]{this}, wktType);
        } else {
            throw new WKTRepresentationException();
        }
    }

    /**
     * Compares this point with another point for equality.
     * Returns true if all the attributes are the same
     *
     * @param p - Point to compare
     * @return {boolean} - true for same Point
     */
    public boolean is(Point p) {
        boolean sameCoordinates = Arrays.equals(coordinates, p.getCoordinates());
        boolean sameDimension = d == p.getDimension();
        boolean sameWktType = p.isWktConform() ? wktType.equals(p.getWktType()) : wktType == null;
        boolean isSame = sameCoordinates && sameWktType && sameDimension;
        if (!(m == null)) {
            isSame = isSame && (m == p.getLrsValue());
        }
        return isSame;
    }

    /**
     * Distance from this point to another
     * @param p - other point
     * @return - distance in coordinate units
     */
    public double distanceTo(Point p) {
		double xSquare = Math.pow(p.getX() - getX(), 2);
		double ySquare = Math.pow(p.getY() - getY(), 2);
        return Math.sqrt(xSquare + ySquare);
	}

    /**
     * Calculates the angle from this point to another as
     * east aligned angle with values between +pi and -pi
     * @param p
     * @return
     */
    public double angle(Point p) {
        double ox = p.getX() - this.getX();
        double oy = p.getY() - this.getY();
        double rad = Math.atan2(oy, ox);
        return (rad * 180 / Math.PI);
    }

    /**
     * Calculates bearing from this point to another as
     * north aligned angle with values between 0 and 360
     * @param p
     * @return
     */
    public double nAngle(Point p) {
        return Buffer.normalizeAngle(this.angle(p));
    }

}
