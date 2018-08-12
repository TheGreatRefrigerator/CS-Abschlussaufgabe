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
    	String[] wkt = wktString.split("[()]"); 
    	String type = wkt[0].split(" ")[1]; 
    	String[] coordinates = wkt[1].split(" ");
    	
    	
    	double[] coord = new double[coordinates.length]; 
    	
    	for(int i=0; i < coord.length; i++) {
    		coord[i] = Double.parseDouble(coordinates[i]);
    	}
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
//    public static Point createPoint(double... coords) {
//    	return new Point(coords);
//	}
    public static Point createPoint(double[] coords)
    {
    	return new Point(coords);
	}

    public static Line createLine(String wkt) {
		return new Line(wkt);
    }
    public static Line createLine(double... coords) {
        return new Line();
    }
////    public static Line createLine(double[] coords) {
////
////    };
    public static Polygon createPolygon(String wkt) {
		return new Polygon(wkt);
    }
    public static Polygon createPolygon(double... coords) {
		return new Polygon();
    }

//    public static Polygon createPolygon(double[] coords) {
//
//    };


}

