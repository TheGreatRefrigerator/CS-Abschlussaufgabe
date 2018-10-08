package GeometryFactory;

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

    static String trimString(String wkt) {
        return wkt.replace(".0", "");
    }

    static void buildPointCoordinatesString(StringBuilder wkt, Point p) {
        double[] pointCoordinates = p.getCoordinates();
        Double mValue = p.hasLrsValue() ? p.getLrsValue() : null;
        for (int j = 0; j < pointCoordinates.length; j++) {
            double i = pointCoordinates[j];
            wkt.append(String.valueOf(i));
            if (j < pointCoordinates.length - 1) {
                wkt.append(" ");
            }
        }
        // append measure value if it exists
        if (p.hasLrsValue()) {
            wkt.append(' ').append(mValue.toString());
        }
    }
}
