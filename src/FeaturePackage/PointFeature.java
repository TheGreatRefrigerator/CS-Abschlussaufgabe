package FeaturePackage;

import GeometryFactory.Geometry;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

public class PointFeature implements Feature {
//    private Geometry<Point>

    public PointFeature(String wkts ) {
        //TODO: create poits from multiple wkts and save in Point[] attribute

    }

    @Override
    public Polygon buffer() {
        return null;
    }
}
