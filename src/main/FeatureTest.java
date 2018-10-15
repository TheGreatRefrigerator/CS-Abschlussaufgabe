package main;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import FeaturePackage.LineFeature;
import FeaturePackage.PointFeature;
import FeaturePackage.PolygonFeature;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

class FeatureTest {
    static void featureTest() {
        System.out.println();
        System.out.println("---Test PointFeature---");
        try {
            System.out.println("Create PointFeature x with new PointFeature(\"Heidelberg\", \"POINT (8.685297 49.416404)\");");
            PointFeature x = new PointFeature("Heidelberg", "POINT (8.685297 49.416404)");
            System.out.println("Call x.getInfo(): " + x.getInfo());
            System.out.println("Call x.setInfo(\"The beautiful city Heidelberg which has an amazing castle.\"): ");
            x.setInfo("The beautiful city Heidelberg which has an amazing castle.");
            System.out.println("Call x.getInfo(): " + x.getInfo());
            System.out.println("Call x.getWKT(): " + x.getWKT());
            System.out.println("Call x.getName(): " + x.getName());
            System.out.println("Call x.getGeometry().getDimension(): " + x.getGeometry().getDimension());
            System.out.println("Call x.getGeometry().getX(): " + x.getGeometry().getX());
            System.out.println("Call x.getGeometry().getY(): " + x.getGeometry().getY());
            System.out.println("Call x.buffer(0.1).getWKT() -> range=0.1 :\n" + x.buffer(0.1).getWKT());
            // This buffer looks oval because of coordinate system distortion
            System.out.println();
            System.out.println("---Test LineFeature---");
            System.out.println("Create LineFeature l with new LineFeature(\"line\", \"LINESTRING (0 -9,-5 -9,0 5,5 5,1 8,8 15,10 10)\");");
            LineFeature l = new LineFeature("line", "LINESTRING (0 -9,-5 -9,0 5,5 5,1 8,8 15,10 10)");
            System.out.println("Call l.getInfo(): " + l.getInfo());
            System.out.println("Call l.getWKT(): " + l.getWKT());
            System.out.println("Call l.setName(\"Snake\")");
            l.setName("Snake");
            System.out.println("Call l.getName(): " + l.getName());
            System.out.println("Call l.buffer(2).getWKT() -> range=2 :\n" + l.buffer(2, 1).getWKT());
            // Buffer function not yet exact for complex lines where buffer would have inner rings
            // Also big ranges in combination with sharp angles will result in funny buffers
            System.out.println();
            System.out.println("---Test PolygonFeature---");
            System.out.println("Create Points x1=(1, 1), x2=(1, -1), x3=(-1, -1) and x4=(-1, 1)");
            Point x1 = GeometryFactory.createPoint(1, 1);
            Point x2 = GeometryFactory.createPoint(1, -1);
            Point x3 = GeometryFactory.createPoint(-1, -1);
            Point x4 = GeometryFactory.createPoint(-1, 1);
            System.out.println();
            System.out.println("Create PolygonFeature square with new PolygonFeature(\"square\",\"This is a square created from multiple point geometries\", x1,x2,x3,x4,x1);");
            PolygonFeature square = new PolygonFeature("square", "This is a square created from multiple point geometries", x1, x2, x3, x4, x1);
            System.out.println("Call square.getWKT() :\n" + square.getWKT());
            System.out.println("Call square.buffer(1,1).getWKT() -> range=1, smoothness=1 :\n" + square.buffer(1, 1).getWKT());
            System.out.println("Call square.buffer(4,10).getWKT() -> range=4, smoothness=10 :\n" + square.buffer(4, 10).getWKT());
            System.out.println("Call square.buffer(0.5,true).getWKT() -> range=4, inside=true :\n" + square.buffer(0.5, true).getWKT());
            System.out.println();
            System.out.println("Create PolygonFeature square with name=star and WKT=POLYGON((6.4432647238973 29.48779401608338,9.6073272238973 41.992475443919304,12.4198272238973 28.873910847248524,27.2733428488973 34.76903984368385,19.2752959738973 25.433736431564615,27.0096709738973 15.65518403896567,11.3651397238973 27.40140891699139,13.8260772238973 14.80715939499396,6.1795928488973 25.433736431564615,-2.1700165261026996 29.258018506443854,6.4432647238973 29.48779401608338))");
            PolygonFeature star = new PolygonFeature("star", "POLYGON((6.4432647238973 29.48779401608338,9.6073272238973 41.992475443919304,12.4198272238973 28.873910847248524,27.2733428488973 34.76903984368385,19.2752959738973 25.433736431564615,27.0096709738973 15.65518403896567,11.3651397238973 27.40140891699139,13.8260772238973 14.80715939499396,6.1795928488973 25.433736431564615,-2.1700165261026996 29.258018506443854,6.4432647238973 29.48779401608338))");
            System.out.println("Call star.buffer(2,4).getWKT() -> range=2, smoothness=4 :\n" + star.buffer(2, 4).getWKT());


        } catch (WktInvalidException | WKTRepresentationException | InvalidPolygonException | DimensionalException e) {
            e.printStackTrace();
        }
    }
}
