package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Polygon;
import GeometryFactory.Point;

public class PolygonFeature implements Feature {
    private String name;
    private Polygon geometry;
    private String info;
    // Default info text
    private static final String infoText = "This is a Polygon";

    public PolygonFeature(String name, String wkt) throws WktInvalidException {
        this(name, infoText, wkt);
    }

    public PolygonFeature(String name, String info, String wkt) throws WktInvalidException {
        geometry = GeometryFactory.createPolygon(wkt);
        this.name = name;
        this.info = info;
    }


    public PolygonFeature(String name, double... coords) throws DimensionalException, InvalidPolygonException {
        this(name, infoText, coords);
    }

    public PolygonFeature(String name, String info, double[]... coords) throws DimensionalException, InvalidPolygonException {
        geometry = GeometryFactory.createPolygon(coords);
        this.name = name;
        this.info = info;
    }

    public PolygonFeature(String name, Point... points) throws DimensionalException, InvalidPolygonException {
        this(name, infoText, points);
    }

    public PolygonFeature(String name, String info, Point... points) throws DimensionalException, InvalidPolygonException {
        geometry = GeometryFactory.createPolygon(points);
        this.name = name;
        this.info = info;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Polygon getGeometry() {
        return geometry;
    }

    public void setGeometry(Polygon geometry) {
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

    @Override
    public String getWKT() throws WKTRepresentationException {
        return geometry.getWKT();
    }

    @Override
    public Polygon buffer(double range) {
        return buffer(range, 2);
    }

    public Polygon buffer(double range, int smoothness) {
        return Buffer.polygonBuffer(geometry, range, smoothness);
    }
}
