package GeometryFactory;

public interface Geometry {

		
	// Fl�che einer Geometrie 
//		public double Area(); 

	// Zentriod
//		public double Centroid(); 
		
	// Ausdehnung 
//		public double Extent();	


    String getInfo();

    Polygon buffer(double range);

    String getWKT();
}
