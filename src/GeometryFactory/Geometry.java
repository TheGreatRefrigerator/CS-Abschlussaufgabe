package GeometryFactory;

import Exceptions.WKTRepresentationException;

public interface Geometry {

	String getWKT() throws WKTRepresentationException;
	boolean isWktConform();
	// Fläche einer Geometrie
//		public double Area(); 

	// Zentriod
//		public double Centroid(); 
		
	// Ausdehnung 
//		public double Extent();	


}