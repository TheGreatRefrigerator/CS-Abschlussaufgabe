package GeometryFactory;

public class Polygon implements Geometry {
    private String info;

    Polygon(){
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

    @Override
    public String getWKT() {
        return null;
    }
}
