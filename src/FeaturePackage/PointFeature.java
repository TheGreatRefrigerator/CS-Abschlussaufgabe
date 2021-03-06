package FeaturePackage;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

public class PointFeature implements Feature {
    /*--Attributes--*/
    private String name;
    private Point geometry;
    private String info;
    // Default info text
    private static final String infoText = "This is a point";

    /*--Constructors--*/

    /**
     * Construct point feature from WKT with default info
     * @param name - the feature name
     * @param wkt  - the WKT string
     * @throws WktInvalidException - in case of invalid WKT
     */
    public PointFeature(String name, String wkt) throws WktInvalidException {
        this(name, infoText, wkt);
    }

    public PointFeature(String name, String info, String wkt) throws WktInvalidException {
        geometry = GeometryFactory.createPoint(wkt);
        this.name = name;
        this.info = info;
    }

    public PointFeature(String name, Point p) {
        this(name, infoText, p);
    }

    public PointFeature(String name, String info, Point p) {
        geometry = p;
        this.name = name;
        this.info = info;
    }

    public PointFeature(String name, double... coords) {
        this(name, infoText, coords);
    }

    public PointFeature(String name, String info, double... coords) {
        geometry = GeometryFactory.createPoint(coords);
        this.name = name;
        this.info = info;
    }

    /*--Getter & Setter--*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    /*--Methods--*/

    @Override
    public String getWKT() throws WKTRepresentationException {
        return geometry.getWKT();
    }

    @Override
    public Polygon buffer(double range) {
        return buffer(range, 2);
    }

    public Polygon buffer(double range, int smoothness) {
        return Buffer.pointBuffer(geometry, range, smoothness);
    }

}
