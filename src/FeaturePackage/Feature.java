package FeaturePackage;

import Exceptions.WKTRepresentationException;
import GeometryFactory.Polygon;

/**
 * Every Feature has Getter & Setter for name and info
 * as well as the buffer() method and the function to
 * return the WKT of the included Geometry.
 * getGeometry() not defined here as it has different return types
 */
public interface Feature {

    String getName();

    void setName(String name);

    String getInfo();

    void setInfo(String info);

    Polygon buffer(double range);

    String getWKT() throws WKTRepresentationException;
}
