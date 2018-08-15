package FeaturePackage;

import GeometryFactory.Geometry;
import GeometryFactory.Polygon;

public interface Feature {
	
    Geometry getGeometry();
    String getInfo();
    Polygon buffer(double range);
    String getWKT();
}
