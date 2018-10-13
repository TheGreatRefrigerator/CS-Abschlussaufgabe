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

public class FeatureTest {
    public static void featureTest() {
        System.out.println();
        System.out.println("---Test FeaturePackage---");
        System.out.println("PointFeature Tests:");
        Point x1 = GeometryFactory.createPoint(3, 4, 6);
        Point x2 = GeometryFactory.createPoint(4, 5, 9);
        Point x3 = GeometryFactory.createPoint(6, 2, 10);
        try {

            PointFeature x = new PointFeature("Heidelberg", "POINT (8.685297 49.416404)");
            PointFeature y = new PointFeature("Point", "Point (3 5)");

            x.setInfo("The beautiful city Heidelberg which has an amazing castle.");
            System.out.println(x.getInfo());
            System.out.println(x.getWKT());
            System.out.println(x.getName());
            System.out.println(x.getGeometry().getDimension());
            System.out.println(x.getGeometry().getX());
            System.out.println(x.getGeometry().getY());
            System.out.println(x.buffer(0.0002).getWKT());
            System.out.println(y.buffer(1, 5).getWKT());
            LineFeature yan = new LineFeature("line", "LINESTRING (0 -9,-5 -9,0 5,5 5,1 8,8 15,3 -4)");
            PolygonFeature square = new PolygonFeature("poly", "POLYGON((6.4432647238973 29.48779401608338,9.6073272238973 41.992475443919304,12.4198272238973 28.873910847248524,27.2733428488973 34.76903984368385,19.2752959738973 25.433736431564615,27.0096709738973 15.65518403896567,11.3651397238973 27.40140891699139,13.8260772238973 14.80715939499396,6.1795928488973 25.433736431564615,-2.1700165261026996 29.258018506443854,6.4432647238973 29.48779401608338))");
            PolygonFeature bla = new PolygonFeature("poly", "POLYGON((12.4198272238973 27.24523957615215,22.0877959738973 29.48779401608338,26.5702178488973 37.466475220360145,11.8045928488973 36.83600768359284,4.8612334738973 29.027725613384003,12.4198272238973 27.24523957615215))");
            PolygonFeature fromPoints = new PolygonFeature("poly", "This is a polygon created from multiple point geometries", x1, x2, x3, x1);

            LineFeature yen = new LineFeature("line", "LINESTRING (1 1,2 2)");
            LineFeature lin = new LineFeature("line", "LINESTRING (5 5,10 10)");
            Polygon buff = square.buffer(2, 0);
            buff = bla.buffer(0.005, 6);
            Polygon buff2 = yen.buffer(2, 0);
            System.out.println(buff.getWKT());
            System.out.println(buff2.getWKT());
            System.out.println(yan.buffer(2, 1).getWKT());
            System.out.println(buff.getWKT());
            Point[] points = lin.buffer(2, 1).getPoints();

        } catch (WktInvalidException | WKTRepresentationException | DimensionalException | InvalidPolygonException e) {
            e.printStackTrace();
        }
    }
}
