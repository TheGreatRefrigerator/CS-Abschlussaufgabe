package FeaturePackage;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Polygon;
import GeometryFactory.Point;

public class PolygonFeature implements Feature {
    /*--Attributes--*/
    private String name;
    private Polygon geometry;
    private String info;
    /*--Constant--*/
    // Default info text
    private static final String infoText = "This is a Polygon";

    /*--Constructors--*/
    /**
     * Construct polygon feature from WKT with default info
     *
     * @param name - the feature name
     * @param wkt  - the WKT string
     * @throws WktInvalidException - in case of invalid WKT
     */
    public PolygonFeature(String name, String wkt) throws WktInvalidException {
        this(name, infoText, wkt);
    }

    /**
     * Construct polygon feature from WKT
     *
     * @param name - the feature name
     * @param info - the info text
     * @param wkt  - the WKT string
     * @throws WktInvalidException - in case of invalid WKT
     */
    public PolygonFeature(String name, String info, String wkt) throws WktInvalidException {
        geometry = GeometryFactory.createPolygon(wkt);
        this.name = name;
        this.info = info;
    }

    /**
     * Create polygon feature from double arrays with default info
     *
     * @param name   - the feature name
     * @param coords - the coordinates [x1,y1],[x2,y2],..,[x1,y1]
     * @throws DimensionalException    - for combining coordinates of different dimension
     * @throws InvalidPolygonException - for invalid polygon creation
     */
    public PolygonFeature(String name, double[]... coords) throws DimensionalException, InvalidPolygonException {
        this(name, infoText, coords);
    }

    /**
     * Create polygon feature from double arrays with default info
     *
     * @param name   - the feature name
     * @param coords - the coordinates [x1,y1],[x2,y2],..,[x1,y1]
     * @param info   - the info text
     * @throws DimensionalException    - for combining coordinates of different dimension
     * @throws InvalidPolygonException - for invalid polygon creation
     */
    public PolygonFeature(String name, String info, double[]... coords) throws DimensionalException, InvalidPolygonException {
        geometry = GeometryFactory.createPolygon(coords);
        this.name = name;
        this.info = info;
    }

    /**
     * Create polygon feature from multiple point geometries.
     *
     * @param name   - the feature name
     * @param points - the points e.g. p1, p2, p3, ..., p1
     * @throws DimensionalException    - for points of different dimension
     * @throws InvalidPolygonException - for invalid polygon creation
     */
    public PolygonFeature(String name, Point... points) throws DimensionalException, InvalidPolygonException {
        this(name, infoText, points);
    }

    /**
     * Create polygon feature from multiple point geometries.
     *
     * @param name   - the feature name
     * @param info   - the info text
     * @param points - the points e.g. p1, p2, p3, ..., p1
     * @throws DimensionalException    - for points of different dimension
     * @throws InvalidPolygonException - for invalid polygon creation
     */
    public PolygonFeature(String name, String info, Point... points) throws DimensionalException, InvalidPolygonException {
        geometry = GeometryFactory.createPolygon(points);
        this.name = name;
        this.info = info;
    }

    /*--Getter & Setter--*/
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

    /*--Methods--*/
    /**
     * Generates a Buffer around the Polygon with specified distance
     * and a smoothness of 2
     *
     * @param distance - the buffer distance
     * @return {Polygon} - the buffer polygon
     */
    public Polygon buffer(double distance) {
        return buffer(distance, 2);
    }

    /**
     * Generates a Buffer around the Polygon with specified distance.
     * The corner smoothness can be adjusted for less edgy geometries
     *
     * @param distance   - the buffer distance
     * @param smoothness - corner smoothness; default: 2
     * @return {Polygon} - the buffer polygon
     */
    public Polygon buffer(double distance, int smoothness) {
        return buffer(distance, smoothness, false);
    }

    /**
     * Generates a Buffer around or inside the Polygon with specified distance
     * and a smoothness of 2
     *
     * @param distance - the buffer distance
     * @param inside   - create buffer inside the polygon
     * @return {Polygon} - the buffer polygon
     */
    public Polygon buffer(double distance, boolean inside) {
        return buffer(distance, 2, inside);
    }

    /**
     * Generates a Buffer around or inside the Polygon with specified distance.
     * The corner smoothness can be adjusted for less edgy geometries
     *
     * @param distance   - the buffer distance
     * @param smoothness - corner smoothness; default: 2
     * @param inside     - create buffer inside the polygon
     * @return {Polygon} - the buffer polygon
     */
    public Polygon buffer(double distance, int smoothness, boolean inside) {
        return Buffer.polygonBuffer(geometry, distance, smoothness, inside);
    }
}
