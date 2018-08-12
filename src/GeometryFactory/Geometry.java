package GeometryFactory;

public interface Geometry {

		
	// Fläche einer Geometrie
//		public double Area(); 

	// Zentriod
//		public double Centroid(); 
		
	// Ausdehnung 
//		public double Extent();	


    String getInfo();
    // TODO move this to the Feature Interface

    Polygon buffer(double range);
    // TODO move this to the Feature Interface
    String getWKT();
}
