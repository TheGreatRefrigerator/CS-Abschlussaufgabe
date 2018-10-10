package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

public class LineFeature implements Feature {
    private String name;
    private Line geometry;
    private String info;
    // Default info text
    private static final String infoText = "This is a line";

    public LineFeature(String name, String wkt) throws WktInvalidException {
        this(name, infoText, wkt);
    }

    public LineFeature(String name, String info, String wkt) throws WktInvalidException {
        geometry = GeometryFactory.createLine(wkt);
        this.name = name;
        this.info = info;
    }

    public LineFeature(String name, double... coords) throws DimensionalException {
        this(name, infoText, coords);
    }

    public LineFeature(String name, String info, double[]... coords) throws DimensionalException {
        geometry = GeometryFactory.createLine(coords);
        this.name = name;
        this.info = info;
    }

    public LineFeature(String name, Point... points) throws DimensionalException {
        this(name, infoText, points);
    }

    public LineFeature(String name, String info, Point... points) throws DimensionalException {
        geometry = GeometryFactory.createLine(points);
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

    public Line getGeometry() {
        return geometry;
    }

    public void setGeometry(Line geometry) {
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
        return Buffer.lineBuffer(geometry, range, smoothness);
    }
}