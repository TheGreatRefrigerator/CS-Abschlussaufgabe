package GeometryFactory;

public interface GeometryFactory {

    public static Geometry createGeomFromWKT(String wktString){
        Geometry geom = null;
        if (wktString.equals("POINT")) {
            geom = new Polygon();
        } else if (wktString.equals("LINESTRING")) {
            geom = null;
        }
        return geom;
    }
}

