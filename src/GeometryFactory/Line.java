package GeometryFactory;

import java.awt.Point;

public class Line implements Geometry {
	private Point start, end; 
	
	/* Entweder über zwei Punkte */ 
	public Line (Point start, Point end) { 
		this.start = start; 
		this.end = end; }
	
	/* Oder über Koordinaten
	   Start: x1, y1; Ende: x2, y2 */ 
	public Line (double x1, double y1, double x2, double y2) { 
		this.start = new Point(x1, x2); 
		this.end = new Point (x2, y2); }
		
	
	
	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public Polygon buffer(double range) {
		return null;
	}

	@Override
	public String getWKT() {
		return null;
	}

}
