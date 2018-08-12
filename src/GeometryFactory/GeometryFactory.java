package GeometryFactory;

public class GeometryFactory {

//    public static Geometry createGeomFromWKT(String wktString){
//        Geometry geom = null;
//        if (wktString.equals("POINT")) {
//            geom = new Polygon();
//        } else if (wktString.equals("LINESTRING")) {
//            geom = null;
//        }
//        return geom;
//    }

    public static Point createPoint(String wktString) {
        // split wkt on '(' and ')' to separate the Type and the Coordinates
        String[] wkt = wktString.split("[()]");

        // ok this might seem a little complicated :) this is the short writing of the below
        // in general you can say
        // variable = (if this) ? (assign this) : (else assign this);
        // this is called a shorthand if -> https://stackoverflow.com/questions/4461996/short-if-else-statement
        String type = wkt[0].split(" ").length == 2 ? wkt[0].split(" ")[1] : "Normal";

/*      //Create a type variable for the point type
        String type;
        // if you split "POINT " at " ", length will be 1, if you split "POINT Z" length will be 2 -> ["POINT","Z"]
        if (wkt[0].split(" ").length == 2)
          type = wkt[0].split(" ")[1];
        else
          type = "Normal";
*/

        // just for checking
        System.out.println(type);
        //TODO check if type is POINT, POINT Z, POINT M or POINT ZM and else return exception that wkt is no
        //TODO valid point wkt

        // split coords at ' ' to get single values
        String[] coordinates = wkt[1].split(" ");

        // create new double array to save the values that are still in string format
        double[] coord = new double[coordinates.length];

        // loop through and convert string values to doubles
        for (int i = 0; i < coord.length; i++) {
            coord[i] = Double.parseDouble(coordinates[i]);
        }

        // make a new point from the now double coords and return it.
        return new Point(coord);
//    	if(type.equals("M")) {
//    		return Point(coord, type);
//    	}
//    	else if(type.equals("Z")) {
//    		return Point(coord, type);
//    	}
//    	else if(type.equals("ZM")) {
//    		return Point(coord, type);
//    	}
//    	else	{
//    		return Point(coord, type);
//    	}


    }

    /**
     * Creates a new Point with exactly the same constructor as in Point.java for arbitrary dimensions
     * @param coords - the n dimensional coordinates
     * @return - the created Point
     */
    public static Point createPoint(double... coords) {
        return new Point(coords);
    }

    // we will not need this as the above constructor does exactly the same
//    public static Point createPoint(double[] coords) {
//        return new Point(coords);
//    }

    public static Line createLine(String wkt) {
        return new Line(wkt);
    }

    public static Line createLine(double... coords) {
        return new Line();
    }


    public static Polygon createPolygon(String wkt) {
        return new Polygon(wkt);
    }

    public static Polygon createPolygon(double... coords) {
        return new Polygon();
    }

}

