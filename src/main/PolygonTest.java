package main;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Point;
import GeometryFactory.Polygon;


public class PolygonTest {
    public static void polygonTest() {
        System.out.println();
        System.out.println("Polygon Tests:");
        Point x1 = GeometryFactory.createPoint(3, 4, 6);
        Point x2 = GeometryFactory.createPoint(4, 5, 9);
        Point x3 = GeometryFactory.createPoint(6, 2, 10);

        Point[] polyPoints = {x1, x2, x3, x1};
        Point p1 = x1;
        p1.setLrsValue(3.);
        x2.setLrsValue(4.);
        x3.setLrsValue(1.);
        Polygon poly = null;
        Polygon poly2 = null;
        Polygon poly3 = null;

        try {
            poly = GeometryFactory.createPolygon(polyPoints);
            poly2 = GeometryFactory.createPolygon("POLYGON ((35 10,45 45,15 40,10 20,35 10))");
            poly3 = GeometryFactory.createPolygon(new double[][]{{12, 31, 2}, {2, 3.432, 3}, {2, 111, 1}, {11, 43, 1}, {12, 31, 2}});
        } catch (DimensionalException | InvalidPolygonException | WktInvalidException e) {
            e.printStackTrace();
        }

        try {
            if (poly != null) {
                System.out.println(poly.getWKT());
            }
            if (poly2 != null) {
                System.out.println(poly2.getWKT());
            }
            if (poly3 != null) {
                System.out.println(poly3.getWKT());
            }

        } catch (WKTRepresentationException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
