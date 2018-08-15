package GeometryFactory;

public class Point implements Geometry {

    // Initialvariablen
    private double[] coordinates;
    private int d; // dimension
	private Double lrsValue; // see https://en.wikipedia.org/wiki/Well-known_text and https://en.wikipedia.org/wiki/Linear_referencing

    // KonstruktorI mit 2 Argumenten (Koordinaten)
	public Point(double... coords) {
        lrsValue = null;
        d = 0;
        coordinates = coords;
        for (double i : coords) {
            d++;
        }
    }

    // KonstruktorI mit 2 Argumenten (Koordinaten)
	public Point(double lrsValue , double... coords) {
		this.lrsValue = new Double(lrsValue);
        coordinates = coords;
        d = 0;
        for (double i : coords) {
            d++;
        }
	}

    // KonstruktorII mit einem double Array, der die Koordinaten beinhaltet
//    public Point(double[] coords) { // coords = Array-Name
//        coordinates = coords;
//        d = 0;
//        for (double i : coords) {
//            d++;
//        }
//    }
    // TODO: copyconstructor for point with coordinates array
    // Konstruktor für deep-copy von Punkt (-> Punkt b)
    // Parameter p: der zu kopierende Punkt
//	public Point(Point p) {
//		x = p.x;
//		y = p.y;
//	}
//
//	public Point() {
//		x = 0;
//		y = 0;
//	}
    //	// Getter/Setter

    /**
     * Returns the point coordinates
     *
     * @return the coordinates double array
     */
    public double[] getCoordinates() {
        return coordinates;
    }
    
    /**
     * Sets the value for a specific coordinate
     * @param position - the dimensional position (x = 1, y = 2 ...)
     * @param value - the new value
     */
    public void setCoordinates(int position, double value ) {
    	coordinates[position - 1] = value;
    }

    /**
     * @return
     */
    public double getX() {
        return coordinates[0];
    }


    public double getY() {
        return coordinates[1];
    }

    public void setX(double x) {
        coordinates[0] = x;
    }

    public void setY(double y) {
        coordinates[1] = y;
    }

    /**
     * Returns the measure value of the point
     *
     * @return the point value
     */
    public double getLrsValue() {
    	double d = 0;
    	
    	try {
    		d = lrsValue.doubleValue();
    	} catch (NullPointerException e) {
			System.out.println("No Value");
		}
		return d;
    }

    /**
     * Returns the point dimension
     *
     * @return the dimension
     */
    public int getDimension() {
        return d;
    }

    // HIER FANGEN JETZT DIE METHODEN AN.. GLAUBE ICH...
    // -> das ist richtig

    // vererbt von Geometrie
    public String getWKT() {
        /*
        get WKT only if it's a valid dimension (2D, 2D+ M, 3D, 3D + M) https://en.wikipedia.org/wiki/Well-known_text
        'Coordinates for geometries may be 2D (x, y), 3D (x, y, z), 4D (x, y, z, m) with an m value
        that is part of a linear referencing system or 2D with an m value (x, y, m).'
        */
    	System.out.println(lrsValue == null);
        if (d > 1 && d < 4) {
            String wkt = "POINT ";
            if (d == 3) {
            	wkt += 'Z';
            }
            if (lrsValue != null) {
            	wkt += 'M';
            }
            wkt += '(';
            for (int j = 0; j < coordinates.length; j++) {
				double i = coordinates[j];
				wkt += String.valueOf(i);
				if (j < coordinates.length - 1) {
					wkt += " ";
				}
			}
            //TODO: loop through coords
            wkt += ")";
            return wkt;
        }
        else {
            // TODO: no WKT representation exception
            // inform user that point can't be represented in wkt because of wrong dimension 1D or 4D or higher
            return null;
        }
    }


//	// Distanz von diesem zu einem anderen Punkt
//	// Parameter b: anderer Punkt
//	// return: Distanz
//	public double distanceTo(Point b) {
//		double a_quadrat = Math.pow(b.getX() - getX(), 2); // wuuuhu!
//		double b_quadrat = Math.pow(b.getY() - getY(), 2);
//		double dist = Math.sqrt(a_quadrat + b_quadrat);
//		return dist;
//	}
//
//	// Winkel zwischen diesem und einem weiteren Punkt
//	// Parameter b: anderer Punkt
//	// return: Winkel in Grad
//	public double getAngle(Point p) {
//		double angle = Math.toDegrees(Math.atan2(p.y - this.getY(), p.x - this.getX()));
//		if (angle < 0)
//			angle += 360;
//		return angle;
//	}

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

//	// Methode, um Objekte vergleichen -> Hier Punkt mit Referenzpunkt b
//	public boolean equals(Object o) {
//		Point b = (Point) o;
//		return ((this.getX() == b.getX())) && ((this.getY() == b.getX()));
//
//	}

    @Override
    public String getInfo() {
        // TODO this will be moved to the feature as the geometry should not have additional information itself
        return null;
    }

    @Override
    public Polygon buffer(double range) {
        // TODO Auto-generated method stub
        return null;
    }

}
