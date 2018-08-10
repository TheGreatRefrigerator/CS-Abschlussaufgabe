package GeometryFactory;

public interface Geometry {

    String getInfo();

    Polygon buffer(double range);
    String getWKT();
}
