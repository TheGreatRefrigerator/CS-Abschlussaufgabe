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

		public static Point createPoint(String wktString) {
    	String[] wkt = wktString.split("[()]"); 
    	String type = wkt[0].split(" ")[1]; 
    	String[] coordinates = wkt[1].split(" ");
    	
    	
    	double[] coord = new double[coordinates.length]; 
    	
    	for(int i=0; i < coord.length; i++) {
    		coord[i] = Double.parseDouble(coordinates[i]); }
    	}	
    	
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
    	
    	 
		
		// Line-WKT splitten...
    	public static String[] createLine (String wktLine){ 
    		String[] wkt = wktLine.split("[()]"); 
    		String[] points = wkt[1].split(","); 	
    			for (int i=0; i<points.length; i++)	{	// wenn wir dimensionstechnisch flexibel sein wollen
    				String[] value = points[i].split(" ");  
    				return value; 
    				
    			// ...und in Double umwandeln
    			double[] doubleLine = new double[value.length]; 
    			for (int x=0; x<value.length; x++ )	{
    				doubleLine[x] = Double.parseDouble(value[x]); 
    				}
    			}
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




