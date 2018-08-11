package GeometryFactory;

public class Line implements Geometry {

	// Initialvariablen
	private Point start, end; // Point = wie double/string -> Datentyp und danach die Eingabewerte

	// Konstruktor mit Start und End Punkt // z.B. wenn schon Punkte vorhanden sind
	// Parameter1 start -> Startpunkt
	// Parameter2 end -> Endpunkt
	public Line(Point start, Point end) { // Line, weil der Konstruktor wie die KLasse heiﬂen muss
		this.start = start; // als Klassenvariable speichern
		this.end = end;
	}

	// Konstruktor mit Koordinaten // wenn wir neue Punkte mit Koordinaten
	// definieren wollen
	// Start: (x1/y1)
	// End: (x2/y2)
	public Line(double x1, double y1, double x2, double y2) {
		this.start = new Point(x1, y1);
		this.end = new Point(x2, y2);
	}

	// METHODEN

	// Returns Startpunkt und Endpunkt
	public Point getStart() {
		return start;
	} // returnt einen Punkt, keine doubles(also keine Koordinaten)

	public Point getEnd() {
		return end;
	}

	// Returns WKT Repr‰sentation des line string
	public String getWKT() {
		return "LINESTRING (" + String.valueOf(start.getX()) + " " + String.valueOf(start.getY()) + ", "
				+ String.valueOf(end.getX()) + " " + String.valueOf(end.getY()) + ")";
	}

	// Neuer Endpunkt
	public void setEnd(Point end) {
		this.end = end;
	}

	// Neuer Startpunkt
	public void setStart(Point start) {
		this.start = start;
	}

	// Fl‰che einer Geometrie
	public double Area() {
		return 0;
	}

	// Ausdehnung
	public double Extent() {
		return start.distanceTo(end);
	};

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public Polygon buffer(double range) {
		return null;
	}

}
