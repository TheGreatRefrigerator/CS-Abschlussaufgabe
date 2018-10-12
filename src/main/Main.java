package main;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WktInvalidException;
import Exceptions.WKTRepresentationException;
import FeaturePackage.LineFeature;
import FeaturePackage.PointFeature;
import FeaturePackage.PolygonFeature;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Polygon;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Point Tests:");
//        Point p = GeometryFactory.createPoint(23.4, 123.2, 3, 42, 1, 54, 3);
//        Point m = GeometryFactory.createPointM(2, 1232, 33);
//        Point n = null;
//        try {
//            n = GeometryFactory.createPoint("POINT M(1232 33 2)");
//        } catch (WktInvalidException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(p.getWktType());
//        // use assertion tests ?
//        // assert p.getWktType() == null;
//        System.out.println(p.getDimension());
//        System.out.println(p.getX());
//        p.setX(10.1);
//        System.out.println(p.getX());
//        p.setX(23.4);
//        System.out.println(p.getX());
//
//        System.out.println(m.getWktType());
//        System.out.println(m.getDimension());
//        try {
//            System.out.println(m.getWKT());
//        } catch (WKTRepresentationException e1) {
//
//            e1.printStackTrace();
//        }
//
//        System.out.println(n.is(m));
//        n.setX(32);
//        System.out.println(n.getX());
//        System.out.println(n.is(m));
//
//        Point q = null;
//        try {
//            q = GeometryFactory.createPoint("POINT ZM(30 10 9 2)");
//        } catch (WktInvalidException e) {
//            e.printStackTrace();
//        }
//        // Test p and q
//        System.out.println("Point p coords should be 23.4, 123.2, 3, 42, 1, 54, 3");
//        for (double cord : p.getCoordinates()) {
//            System.out.print(cord + ", ");
//        }
//        System.out.println();
//
//        System.out.println("Point m coords should be 1232, 33");
//        for (double cord : m.getCoordinates()) {
//            System.out.print(cord + ", ");
//        }
//        try {
//            System.out.println(m.getWKT());
//        } catch (WKTRepresentationException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Point m's value:" + m.getLrsValue());
//
//        System.out.println("Point q coords should be 30 10");
//        for (double cord : q.getCoordinates()) {
//            System.out.print(cord + ", ");
//        }
//        System.out.println("Point q's value: " + q.getLrsValue());
//
//        System.out.println();
//        System.out.println("Line Tests:");
//        Line l, s;
//        try {
//            // examples for line creation
//            l = GeometryFactory.createLine("LINESTRING Z(12 31 2,2 3 3,2 1 1,12 5 1)");
//            s = GeometryFactory.createLine(new double[][]{{12, 31, 2}, {2, 3.432, 3}, {2, 111, 1}, {11, 43, 1}});
//            System.out.println(s.getWktType());
//            try {
//                System.out.println(l.getWKT());
//                System.out.println(s.getWKT());
//            } catch (WKTRepresentationException e) {
//                e.printStackTrace();
//            }
//        } catch (WktInvalidException | DimensionalException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println();
//        System.out.println("Polygon Tests:");
        Point x1 = GeometryFactory.createPoint(3, 4, 6);
        Point x2 = GeometryFactory.createPoint(4, 5, 9);
        Point x3 = GeometryFactory.createPoint(6, 2, 10);
//
//        Point[] polyPoints = {x1, x2, x3, x1};
//        Point p1 = x1;
//        p1.setLrsValue(3.);
//        x2.setLrsValue(4.);
//        x3.setLrsValue(1.);
//        Polygon poly = null;
//        Polygon poly2 = null;
//        Polygon poly3 = null;
//
//        try {
//            poly = GeometryFactory.createPolygon(polyPoints);
//            poly2 = GeometryFactory.createPolygon("POLYGON ((35 10,45 45,15 40,10 20,35 10))");
//            poly3 = GeometryFactory.createPolygon(new double[][]{{12, 31, 2}, {2, 3.432, 3}, {2, 111, 1}, {11, 43, 1}, {12, 31, 2}});
//        } catch (DimensionalException | InvalidPolygonException | WktInvalidException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            if (poly != null) {
//                System.out.println(poly.getWKT());
//            }
//            if (poly2 != null) {
//                System.out.println(poly2.getWKT());
//            }
//            if (poly3 != null) {
//                System.out.println(poly3.getWKT());
//            }
//
//        } catch (WKTRepresentationException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println();
//        System.out.println();
        System.out.println("---Test FeaturePackage---");
        System.out.println("PointFeature Tests:");
        try {
//            PointFeature x = new PointFeature("Heidelberg", "POINT (8.685297 49.416404)");
//            PointFeature y = new PointFeature("Point", "Point (3 5)");
            LineFeature yan = new LineFeature("line", "LINESTRING (0 -9,-5 -9,0 5,5 5,1 8,8 15,3 -4)");
            PolygonFeature square = new PolygonFeature("poly", "POLYGON((6.4432647238973 29.48779401608338,9.6073272238973 41.992475443919304,12.4198272238973 28.873910847248524,27.2733428488973 34.76903984368385,19.2752959738973 25.433736431564615,27.0096709738973 15.65518403896567,11.3651397238973 27.40140891699139,13.8260772238973 14.80715939499396,6.1795928488973 25.433736431564615,-2.1700165261026996 29.258018506443854,6.4432647238973 29.48779401608338))");
			PolygonFeature bla = new PolygonFeature("poly", "POLYGON((12.4198272238973 27.24523957615215,22.0877959738973 29.48779401608338,26.5702178488973 37.466475220360145,11.8045928488973 36.83600768359284,4.8612334738973 29.027725613384003,12.4198272238973 27.24523957615215))");
			PolygonFeature fromPoints = new PolygonFeature("poly", "This is a polygon created from multiple point geometries", x1, x2, x3, x1);

//            LineFeature yen = new LineFeature("line", "LINESTRING (1 1,2 2)");
//            LineFeature lin = new LineFeature("line", "LINESTRING (5 5,10 10)");
            Polygon buff = square.buffer(2, 0);
            buff = bla.buffer(2, 6);
//            Polygon buff2 = yen.buffer(2, 0);
            System.out.println(buff.getWKT());
//            System.out.println(buff2.getWKT());
//            x.setInfo("The beautiful city Heidelberg which has an amazing castle.");
//            System.out.println(x.getInfo());
//            System.out.println(x.getWKT());
//            System.out.println(x.getName());
//            System.out.println(x.getGeometry().getDimension());
//            System.out.println(x.getGeometry().getX());
//            System.out.println(x.getGeometry().getY());
//            System.out.println(x.buffer(0.0002).getWKT());
//            System.out.println(y.buffer(1, 5).getWKT());
//            System.out.println(yan.buffer(2, 1).getWKT());
//            System.out.println(lin.buffer(2, 1).getWKT());
//            Point[] points = lin.buffer(2, 1).getPoints();

        } catch (WktInvalidException | WKTRepresentationException | DimensionalException | InvalidPolygonException e) {
            e.printStackTrace();
        }

    }
}
