package GeometryFactory;

public class Point implements Geometry {

	// Initialvariablen
	private double x, y;

	// KonstruktorI mit 2 Argumenten (Koordinaten)
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// KonstruktorII mit einem double Array, der die Koordinaten beinhaltet
	public Point(double[] coords) { // coords = Array-Name
		if (coords.length == 2) {
			this.x = coords[0];
			this.y = coords[1];
		} else { // Default, wenn zu wenig/viele Koordinaten eingegeben werden
			this.x = 0;
			this.y = 0;
		}
	}

	// Konstruktor für deep-copy von Punkt (-> Punkt b)
	// Parameter p: der zu kopierende Punkt
	public Point(Point p) {
		x = p.x;
		y = p.y;
	}

	// HIER FANGEN JETZT DIE METHODEN AN.. GLAUBE ICH...

	// vererbt von Geometrie
	public String getWKT() {
		String wkt = "Punkt (" + String.valueOf(getX()) + "," + String.valueOf(getY()) + ")";
		return wkt;
	}

	// Getter/Setter
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX() {
		this.x = x;
	}

	public void setY() {
		this.y = y;
	}

	// Distanz von diesem zu einem anderen Punkt
	// Parameter b: anderer Punkt
	// return: Distanz
	public double distanceTo(Point b) {
		double a_quadrat = Math.pow(b.getX() - getX(), 2); // wuuuhu!
		double b_quadrat = Math.pow(b.getY() - getY(), 2);
		double dist = Math.sqrt(a_quadrat + b_quadrat);
		return dist;
	}

	// Winkel zwischen diesem und einem weiteren Punkt
	// Parameter b: anderer Punkt
	// return: Winkel in Grad
	public double getAngle(Point p) {
		double angle = Math.toDegrees(Math.atan2(p.y - this.getY(), p.x - this.getX()));
		if (angle < 0)
			angle += 360;
		return angle;
	}

	// Fläche
	public double Area() {
		return 0;
	}

	// Ausdehnung
	public double Extent() {
		return 0;
	}

	// Zentriod
	// public double Centroid() {
	// ? return this; }

	// Methode, um Objekte vergleichen -> Hier Punkt mit Referenzpunkt b
	public boolean equals(Object o) {
		Point b = (Point) o;
		return ((this.getX() == b.getX())) && ((this.getY() == b.getX()));

	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Polygon buffer(double range) {
		// TODO Auto-generated method stub
		return null;
	}

}
