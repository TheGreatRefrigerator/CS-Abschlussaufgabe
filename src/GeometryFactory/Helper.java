package GeometryFactory;

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
}
