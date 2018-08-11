package GeometryFactory;

public interface GeometryFactory {

//    public static Geometry createGeomFromWKT(String wktString){
//        Geometry geom = null;
//        if (wktString.equals("POINT")) {
//            geom = new Polygon();
//        } else if (wktString.equals("LINESTRING")) {
//            geom = null;
//        }
//        return geom;
//    }

    public static Point createPoint(String wkt) {
    	String wktString = wkt.split("[()]"); 

    }
//    public static Point createPoint(double... coords)
//    public static Point createPoint(double[] coords)

//    public static Line createLine(String wkt) {
//
//    };
//    public static Line createLine(double... coords) {
//
//    };
////    public static Line createLine(double[] coords) {
////
////    };
//    public static Polygon createPolygon(String wkt) {
////        if (wkt.split())
//
//    };
//    public static Polygon createPolygon(double... coords) {
//
//    };
//    public static Polygon createPolygon(double[] coords) {
//
//    };


}

