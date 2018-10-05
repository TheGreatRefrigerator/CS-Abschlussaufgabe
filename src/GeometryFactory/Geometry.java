package GeometryFactory;

import Exceptions.WKTRepresentationException;

public interface Geometry {

	public String getWKT() throws WKTRepresentationException;
	// Fläche einer Geometrie
//		public double Area(); 

	// Zentriod
//		public double Centroid(); 
		
	// Ausdehnung 
//		public double Extent();	


}