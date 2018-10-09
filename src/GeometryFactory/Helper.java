package GeometryFactory;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;

public class Helper {
    /**
     * Checks if wkt conform and returns the wkt type or null if not wkt conform
     * @param dimension - the dimension of the object
     * @param mValue - the measure value of the object
     * @return {String|null} - the wkt type to return
     */
    public static String getWktType(int dimension, Double mValue) {
        String wktType = "";
        if (dimension < 4 && dimension > 1) {
            if (dimension == 3) {
                wktType = "Z";
            }
            if (mValue != null) {
                wktType += "M";
            }
        } else {
            wktType = null;
        }
        return wktType;
    }

    // if no mValue is passed default to mValue = null
    public static String getWktType(int dimension) {
        return getWktType(dimension, null);
    }

    /**
     *
     * @param type - the WKT type of the Geometry ("", "Z", "M" or "ZM")
     * @param length - the number of given values
     * @throws WktInvalidException -
     */
    static void checkWktValidity(String type, int length) throws WktInvalidException {
        // check if wkt is valid
        switch (type) {
            case "":
                if (length != 2) throw new WktInvalidException();
                break;
            case "M":
            case "Z":
                if (length != 3) throw new WktInvalidException();
                break;
            case "ZM":
                if (length != 4) throw new WktInvalidException();
                break;
        }
    }

    /**
     *
     * @param wkt
     * @param p
     */
    static void buildPointWktPart(StringBuilder wkt, Point p) {
        double[] pointCoordinates = p.getCoordinates();
        Double mValue = p.hasLrsValue() ? p.getLrsValue() : null;
        for (int j = 0; j < pointCoordinates.length; j++) {
            double i = pointCoordinates[j];
            String valueString = String.valueOf(i);
            // Cleans up trailing zeroes for whole doubles
            // if whole double value e.g. 3.0 or 5.0 -> trim to 3 or 5
            // but keep values like 3.05 or 4.0002
            if (valueString.endsWith(".0")) {
                valueString = valueString.replace(".0", "");
            }
            wkt.append(valueString);
            if (j < pointCoordinates.length - 1) {

                wkt.append(" ");
            }
        }
        // append measure value if it exists
        if (p.hasLrsValue()) {
            String mString = mValue.toString();
            if (mString.endsWith(".0")) {
                mString = mString.replace(".0", "");
            }
            wkt.append(' ').append(mString);
        }
    }

    /**
     *
     * @param type
     * @param points
     * @param wktType
     * @return
     * @throws WKTRepresentationException
     */
    static String buildWkt(String type, Point[] points, String wktType) throws WKTRepresentationException {
        /*
         * get WKT only if it's a valid dimension (2D, 2D+ M, 3D, 3D + M)
         * https://en.wikipedia.org/wiki/Well-known_text 'Coordinates for geometries may
         * be 2D (x, y), 3D (x, y, z), 4D (x, y, z, m) with an m value that is part of a
         * linear referencing system or 2D with an m value (x, y, m).'
         */
        if (!(wktType == null)) {
            // point -> "POINT "; polygon -> "POLYGON "
            StringBuilder wkt = new StringBuilder(type.toUpperCase()).append(" ");
            if (!"".equals(wktType)) {
                wkt.append(wktType).append(" ");
            }
            wkt.append("(");
            if (type.equals("polygon")) {
                wkt.append('(');
            }
            for (int i = 0; i < points.length; i++) {
                Point p = points[i];
                Helper.buildPointWktPart(wkt, p);
                if (i < points.length - 1) {
                    wkt.append(", ");
                }
            }
            wkt.append(")");
            if (type.equals("polygon")) {
                wkt.append(')');
            }
            return wkt.toString();
        } else {
            throw new WKTRepresentationException();
        }
    }

    /**
     * Creates a Point Array from a String Array and the WKT type
     * @param type - WKT type of the geometry
     * @param points - String array containing the point values
     * @throws WktInvalidException
     */
    static Point[] generatePointArray(String type, String[] points) throws WktInvalidException {
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
            pointArray[i] = isMeasurePoint ? new Point(lrsValue, pointValues) : new Point(pointValues);

        }
        return pointArray;
    }
}
