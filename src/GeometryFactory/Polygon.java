package GeometryFactory;

// import Bibliotheken 
import java.util.ArrayList; // notwendig? 
import java.util.List;

public class Polygon implements Geometry {
	private String info;

	// Initialvariablen
	private List<Point> points; // Typ Liste + Typ innerhalb der Liste (hier: Punkt; muss einheitlich sein)

	// Konstruktor mit einer Liste von Punkten
	// Parameter: punkte der Punktliste
	public Polygon(List<Point> points) {
		this.points = points;
	}

	// Konstruktor mit einem Array von Punkten
	// Parameter: Array von Punkten
	public Polygon(Point[] points) {
		for (Point p : points) { // Iterieren durch Array, nenne in jeder Iteration den aktuellen Eintrag p ->
									// Iteration 1: Stelle 0; Iteration 2: Stelle 1.....
			this.points.add(p);
		}
	}

	// METHODEN

	// Getter, der die Polygon-Punkte zurückgibt
	public List<Point> getPoints() { // List<Point> -> Rückgabetyp
		return this.points;
	}

	// WKT
	public String getWKT() {
		String wkt = "Polygon( ";
		for (Point p : this.points) {
			wkt += String.valueOf(p.getX()) + " " + String.valueOf(p.getY()) + ",";
		}
		wkt += ")";
		return wkt;
	}

	// Zentriod
	public Point Centroid() {
		double x_centroid = 0.0, y_centroid = 0.0;
		for (Point p : points) {
			x_centroid += p.getX();
			y_centroid += p.getY();
		}
		return new Point(x_centroid / points.size(), y_centroid / points.size());
	}

	Polygon() {
		info = "helljopihhipiiphjhpioo";
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public Polygon buffer(double range) {
		return null;
	}

}
